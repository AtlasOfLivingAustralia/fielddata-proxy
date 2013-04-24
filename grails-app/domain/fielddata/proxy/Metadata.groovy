package fielddata.proxy

class Metadata {

    Integer id
    String key
    String value

    static mapping = {
        version false
        id column : "id"
        key column: "key"
        value column: "value"
    }
    static constraints = {
    }
}
