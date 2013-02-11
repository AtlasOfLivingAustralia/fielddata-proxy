import fielddata.proxy.Survey
/**
 * Created with IntelliJ IDEA.
 * User: god08d
 * Date: 11/09/12
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
class SurveyWorkflow {

    Survey survey;

    String description

    List<Page> pages

    /** Not displayed but created with default values */
    Page defaults

    public SurveyWorkflow(Survey survey) {
        this.survey = survey
    }


    public Page addPage(String title) {

        Page page = new Page(title);
        pages.add(page);
        return page

    }

    public int pageCount() {
        return pages.size()
    }

    static SurveyWorkflow defaultWorkflow(Survey survey) {

        SurveyWorkflow workflow = new SurveyWorkflow(survey)


        Page currentPage = workflow.addPage("Page 1")
        for (fielddata.proxy.Attribute attribute : survey.attributes) {
            if (attribute.typeCode == 'HR') {
                currentPage = addPage("Page "+(workflow.pageCount()+1))
            }
            else {
                currentPage.addAttribute(attribute)
            }
        }

        return workflow

    }

}
