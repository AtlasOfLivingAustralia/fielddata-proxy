package fielddata.proxy

class User {

	transient springSecurityService

    Integer id
    Integer portalId
	String username
	String password
	boolean enabled

    transient accountExpired = false
    transient accountLocked = false
    transient passwordExpired = false

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
        version false
        table 'user_definition'
		password column: '`password`'
        username column: 'name'
        enabled column: 'active'
        id column: 'user_definition_id'
        portalId column : 'portal_id'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUserId(this.id).collect { new Role(it.role) } as Set
	}


	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
