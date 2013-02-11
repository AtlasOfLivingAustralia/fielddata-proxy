import grails.converters.JSON
import com.vividsolutions.jts.geom.Point

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Point) {
            def returnArray = [:]
            returnArray['x'] = it.x
            returnArray['y'] = it.y
            return returnArray
        }
    }
    def destroy = {
    }


}
