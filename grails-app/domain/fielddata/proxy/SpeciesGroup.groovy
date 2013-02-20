package fielddata.proxy

class SpeciesGroup {

    Integer id
    Integer portalId
    String name

    static mapping = {
        version false
        table 'TAXON_GROUP'
        id column: 'TAXON_GROUP_ID'
        portalId column: 'PORTAL_ID'
    }
}
