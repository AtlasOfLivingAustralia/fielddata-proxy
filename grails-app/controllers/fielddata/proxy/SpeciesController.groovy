package fielddata.proxy
import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

class SpeciesController {

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
                speciesList = Survey.executeQuery("select sp, e from Survey surv join surv.species sp left join sp.profileElements e where surv.id = :id "+
                        "and sp.id not in (select survey_sp.id from Survey s2 join s2.species survey_sp where sp.portalId = :portalId and s2.id in (:notIds))" +
                        "and e.type = 'thumb' order by sp.id asc",
                        [notIds: downloadedSurveys, id:params.int("surveyId"), portalId:survey.portalId,  max:max, offset:offset])
            }
            else {
                // If a Survey has no Species specified, the Survey accepts any Species defined in the portal.
                speciesList = Species.executeQuery("from Species sp left join sp.profileElements e " +
                        "where sp.portalId = :portalId and sp.id not in (:notIds) " +
                        "and e.type = 'thumb' order by sp.id asc",
                        [ portalId: survey.portalId, notIds:downloadedSurveys, max:max, offset:offset])
            }

            // The join on the SpeciesProfile association results in an List containing elements of type
            // array of [Species, SpeciesProfile] being returned from the query.
            // It is possible there will be duplicate Species returned (if there is more than one "thumb" profile
            // element)
            result.list = speciesList.collect { results ->

                return [server_id : results[0].id,
                        scientificName : results[0].scientificName,
                        commonName : results[0].commonName,
                        lsid : results[0].sourceId,
                        taxonGroupId : results[0].taxonGroupId,
                        profileImageUUID : results[1].content]
            }
        }
        response.setContentType("application/json")
        render result as JSON
    }
}
