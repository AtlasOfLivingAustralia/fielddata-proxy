package fielddata.proxy

import com.vividsolutions.jts.geom.Point

class GeoMap {
    Integer id
    Integer zoom
    Integer surveyId

    Point center

    static constraints = {
    }

    static mapping = {
        version false
        zoom column : 'zoom'
        id column : 'geo_map_id'
        center column : 'center'
        surveyId column: 'survey_id'
    }
}
