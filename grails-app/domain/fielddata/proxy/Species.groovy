package fielddata.proxy

class Species {

    Integer portalId
    Integer id
    String scientificName
    String commonName
    Integer taxonGroupId
    String sourceId
    String source

    static constraints = {
    }

    static mapping = {
        table 'indicator_species'
        version false
        id column : "indicator_species_id"
        sourceId column: "source_id"
        source column: "source"
        portalId column: "portal_id"
        scientificName column: "scientific_name"
        commonName column:  "common_name"
        taxonGroupId column: "taxon_group_id"
    }
}
