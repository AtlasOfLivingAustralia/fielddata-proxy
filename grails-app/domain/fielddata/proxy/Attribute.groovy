package fielddata.proxy

class Attribute {

    Integer id
    Integer weight
    String name
    String description
    String typeCode
    List<String> options

    static hasMany = {
        [options : AttributeOption]
    }
    static constraints = {
    }

    static mapping = {
        version false
        id column : "attribute_id"
        weight column: "weight"
        name column: "name"
        description column: "description"
        typeCode : column: "type_code"
    }
}
