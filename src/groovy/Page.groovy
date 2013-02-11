
/**
 * Describes a single page of the workflow.
 * A page is composed of a list of questions that will be displayed to the user completing the workflow
 */
class Page {
    String title
    String description

    List<WorkflowAttribute> attributes


    public Page(String title) {
        this.title = title
    }

    public void addAttribute(fielddata.proxy.Attribute attribute) {

        attributes.add(new WorkflowAttribute(attribute))
    }
}
