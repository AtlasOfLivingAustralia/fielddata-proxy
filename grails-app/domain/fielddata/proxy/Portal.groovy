package fielddata.proxy

class Portal {

    Integer id
    String name
    String path

    static constraints = {
    }

    static mapping = {
        version false
        id column : 'portal_identifier'
        name column : 'name'
        path column : 'url_prefix'
    }
}
