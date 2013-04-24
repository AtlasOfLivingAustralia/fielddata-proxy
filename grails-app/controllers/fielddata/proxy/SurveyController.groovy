package fielddata.proxy
import grails.converters.JSON
import groovy.json.JsonSlurper
import org.apache.commons.logging.LogFactory
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair

class SurveyController {

    private static final log = LogFactory.getLog(this)

    static defaultAction = "list"

    def list() {
        def url = "${grailsApplication.config.fieldDataServerUrl}/${params.portal}/webservice/survey/surveysForUser.htm?ident=${params.ident}".toURL()

        log.info("Received: "+url+" from: "+request.getHeader("User-Agent"))
        response.setContentType("application/json")

        render url.getText()
    }

    def get() {
        def surveysOnDevice = params.surveysOnDevice?'&surveysOnDevice='+params.surveysOnDevice:''

        def url = "${grailsApplication.config.fieldDataServerUrl}/${params.portal}/webservice/application/survey.htm?ident=${params.ident}&sid=${params.id}${surveysOnDevice}".toURL()
        doGet(url)
    }

    def getWithoutSpecies() {
        def surveysOnDevice = params.surveysOnDevice?'&surveysOnDevice='+params.surveysOnDevice:''

        def url = "${grailsApplication.config.fieldDataServerUrl}/${params.portal}/webservice/application/surveyDownload.htm?ident=${params.ident}&sid=${params.id}${surveysOnDevice}".toURL()
        doGet(url)
    }

    def doGet(URL url) {
        def groupId
        def groupsUrl = "${grailsApplication.config.fieldDataServerUrl}/${params.portal}/webservice/taxon/getTaxonGroupById.htm?id="

        def JsonSlurper slurper = new JsonSlurper()

        def survey = slurper.parseText(url.getText())
        Set groups = survey.indicatorSpecies.collect({it.taxonGroup})

        survey.groups = groups.collect({slurper.parseText((groupsUrl+it).toURL().getText())})

        def geoMap = GeoMap.findBySurveyId(params.id)

        survey.map = geoMap
        survey.survey_template.Metadata = ""
        def surveyFromDb = Survey.get(params.id);
        survey.imageUrl = surveyFromDb.getLogoUrl()

        response.setContentType("application/json")

        render survey as JSON
    }

    def upload() {
        def url = "${grailsApplication.config.fieldDataServerUrl}/${params.portal}/webservice/application/clientSync.htm"
        HttpClient http = new DefaultHttpClient()
        HttpPost post = new HttpPost(url)
        def postParams = new ArrayList<NameValuePair>()
        postParams.add(new BasicNameValuePair("ident", params.ident))
        postParams.add(new BasicNameValuePair("inFrame", "false"))
        postParams.add(new BasicNameValuePair("syncData", params.syncData))
        post.setEntity(new UrlEncodedFormEntity(postParams))

        response.setContentType("application/json")
        response.outputStream << http.execute(post).getEntity().content
        return null
    }

    def ping() {
        def url = "${grailsApplication.config.fieldDataServerUrl}/webservice/application/ping.htm".toURL()
        log.info("Received: "+url+" from: "+request.getHeader("User-Agent"))

        response.setContentType("application/json")

        render url.getText()
    }

    def download() {
        def urlSuffix
        if (params.uuid) {
            urlSuffix = "downloadByUUID.htm?uuid=${params.uuid}"
        }
        else {
            urlSuffix = "download.htm?className=${params.className}&id=${params.classId}&fileName=${params.fileName}"
        }
        def url = "${grailsApplication.config.fieldDataServerUrl}/${params.portal}/files/${urlSuffix}"

        HttpClient http = new DefaultHttpClient()
        HttpGet get = new HttpGet(url)

        def downloadResponse = http.execute(get)
        response.setContentType(downloadResponse.getHeaders('Content-Type')[0].value)
        response.outputStream << downloadResponse.getEntity().content
        return null
    }



    def login()  {
        def username = URLEncoder.encode(params.username, "UTF-8")
        def portalName = URLEncoder.encode(params.portalName, "UTF-8")
        def url = "${grailsApplication.config.fieldDataServerUrl}/webservice/user/validate.htm?portalName=${portalName}&username=${username}&password=${params.password}".toURL()

        def loginResponse = new JsonSlurper().parseText(url.getText())

        Portal portal = Portal.findById(loginResponse.portal_id)
        loginResponse.portal = portal

        response.setContentType("application/json")
        render loginResponse as JSON
    }

    def fieldguide() {
        def serverURL = "${grailsApplication.config.fieldDataServer}"
        def url = "${serverURL}${grailsApplication.config.fieldDataContextPath}/${params.portal}/fieldguide/taxon.htm?id=${params.id}".toURL()
        println url
        response.setContentType("text/html")

        render url.getText()

    }
}
