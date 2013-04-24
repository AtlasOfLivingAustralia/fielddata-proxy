package fielddata.proxy

class Survey {

    Integer portalId
    Integer id
    String name
    Set attributes
    //Set species

    static hasMany = [metaData : Metadata, attributes : Attribute, species : Species]
    static mapping = {
        version false
        id column : "survey_id"
        name column: "name"
        portalId column : 'portal_id'
        attributes joinTable: [key: 'survey_survey_id', column: 'attributes_attribute_id']
        species joinTable: [name: 'survey_indicator_species', key: 'survey_survey_id', column:  'species_indicator_species_id']
        metaData joinTable: [name: 'survey_metadata', key: 'survey_survey_id', column: 'metadata_id']
    }
    static constraints = {
    }

    /**
     * GORM (at least without customisation) needs the index column to be in the join table if we map
     * Attributes as a List, so instead we map them as a Set and return them ordered using this method.
     * @return a List of the attributes of this survey, ordered by weight.
     */
    public List<Attribute> getOrderedAttributes() {
        List attributes = new ArrayList(attributes);
        Collections.sort(attributes, new Comparator<Attribute>() {
            public int compare(Attribute a1, Attribute a2) {
                return a1.weight.compareTo(a2.weight)
            }
        })
        return attributes
    }

    public String getLogoUrl() {
        Metadata logo = metaData.find({it.key == "SurveyLogo"});
        if (logo && logo.value) {
            return "/survey/download?className=au.com.gaiaresources.bdrs.model.metadata.Metadata&classId=${logo.id}&fileName=${logo.value}"
        }
        return "";
    }

}
