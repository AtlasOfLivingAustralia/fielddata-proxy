package fielddata.proxy

class CensusMethod {

    public static final String CENSUS_METHOD_POINT = "censusMethodPoint";
    /**
     * Can we define a line on the map for this census method
     */
    public static final String CENSUS_METHOD_LINE = "censusMethodLine";
    /**
     * Can we define a polygon on the map for this census method
     */
    public static final String CENSUS_METHOD_POLYGON = "censusMethodPolygon";

    Integer portalId
    Integer id

    static hasMany = [metaData : Metadata]

    static mapping = {
        version false
        id column : "census_method_id"
        portalId column : 'portal_id'
        metaData joinTable: [name: 'census_method_metadata', key: 'census_method_census_method_id', column: 'metadata_id']
    }
    static constraints = {
    }

    boolean supportsPolygon() {
        def result = false
        metaData?.each {
            if (it.key == CENSUS_METHOD_POINT || it.key == CENSUS_METHOD_LINE) {
                result |= Boolean.parseBoolean(it.value)
            }
        }

        return result
    }
}
