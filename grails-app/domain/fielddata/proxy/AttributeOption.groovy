package fielddata.proxy

class AttributeOption {

    Integer id
    String value

    static constraints = {
        id column:'attribute_option_id'
        value column:'value'
    }
}
