package fielddata.proxy
import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

class SpeciesController {

    // The current species for survey webservice doesn't return the taxon group or lsid so we are rolling or own.
    def speciesForSurvey() {

        int max = params.int("maxResults") ?: 100
        int offset = params.int("first") ?: 0

        def downloadedSurveys = [params.surveysOnDevice].flatten().collect{it?.toInteger() ?: 0}

        // Check whether the Survey is constrained to a list of species or not.
        def survey = Survey.get(params.surveyId)

        if (!survey) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        def speciesCount = survey.species.size()

        // Check whether any downloaded surveys where "all taxa" surveys, in which case there is no need to
        // do anything else.
        def speciesPerSurvey = Survey.executeQuery(
                "select s.id, count(sp) from Survey s left join s.species sp where s.id in (:surveys) group by s.id",
                [surveys:downloadedSurveys])


        def result = [count:0, list:[]]

        if (!speciesPerSurvey.find{it[1] == 0}) {


            def speciesList

            if (speciesCount > 0) {
                speciesList = Survey.executeQuery("select sp from Survey surv join surv.species sp where surv.id = :id "+
                        "and sp.id not in (select survey_sp.id from Survey s2 join s2.species survey_sp where s2.id in (:notIds)) order by sp.id asc",
                        [notIds: downloadedSurveys, id:params.int("surveyId"), max:max, offset:offset])
            }
            else {
                // If a Survey has no Species specified, the Survey accepts any Species defined in the portal.
                speciesList = Species.executeQuery("from Species sp where sp.id not in (:notIds) order by sp.id asc",
                        [ notIds:downloadedSurveys, max:max, offset:offset])
            }


            result.list = speciesList.collect { species ->
                return [server_id : species.id,
                        scientificName : species.scientificName,
                        commonName : species.commonName,
                        lsid : species.sourceId,
                        taxonGroupId : species.taxonGroupId]
            }
        }
        response.setContentType("application/json")
        render result as JSON
    }
}
