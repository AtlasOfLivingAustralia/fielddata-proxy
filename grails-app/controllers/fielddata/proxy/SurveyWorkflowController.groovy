package fielddata.proxy

import grails.plugins.springsecurity.Secured

@Secured("ROLE_ADMIN")
class SurveyWorkflowController {

    static defaultAction = "list"

    def springSecurityService

    def list() {
        def user = springSecurityService.getCurrentUser()
        println "Current portal is ${user.portalId}"
        return [surveys:Survey.where({
            portalId == user.portalId
        })]
    }

    def survey() {

        return [survey:Survey.get(params.id)]
    }
}
