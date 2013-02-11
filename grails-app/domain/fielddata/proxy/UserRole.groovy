package fielddata.proxy

import org.apache.commons.lang.builder.HashCodeBuilder

class UserRole implements Serializable {

	Integer userId
	String role

	boolean equals(other) {
		if (!(other instanceof UserRole)) {
			return false
		}

		other.user?.id == user?.id &&
			other.role == role
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (role) builder.append(role)
		builder.toHashCode()
	}

	static UserRole get(long userId, String roleId) {
		find 'from UserRole where user.id=:userId and role=:roleId',
			[userId: userId, roleId: roleId]
	}


	static mapping = {
        role column : 'role_name'
        userId  column : 'user_definition_id'
		id composite: ['userId', 'role']
		version false
	}
}
