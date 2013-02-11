import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority

/**
 * Extends the regular GrailsUser to also store the portal in which the user is registered.
 */
class FieldDataUser extends GrailsUser {

    Integer portalId

    public FieldDataUser(String username, String password, boolean enabled,
                      Collection<GrantedAuthority> authorities, Object id, Integer portalId) {
        super(username, password, enabled, true, true, true, authorities, id);
        this.portalId = portalId;
    }

}
