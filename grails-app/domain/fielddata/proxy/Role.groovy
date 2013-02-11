package fielddata.proxy

class Role {

	String authority

    public Role(String authority) {
        this.authority = authority
    }

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
