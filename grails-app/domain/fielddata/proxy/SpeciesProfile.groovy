package fielddata.proxy

/**
 * Stores information about a Species.
 */
class SpeciesProfile {

    Integer id
    String type
    String content

    static belongsTo = Species

    static constraints = {
    }

    static mapping = {
        version false
        table 'species_profile'
        id column: 'species_profile_id'
    }
}
