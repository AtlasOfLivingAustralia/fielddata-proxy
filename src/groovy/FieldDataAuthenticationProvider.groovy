import fielddata.proxy.User
import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Extends the GormUserDetailsService to both use the supplied portal id to select the correct user and to
 * add the portal id to the Principal for use by the system.
 */
class FieldDataAuthenticationProvider extends GormUserDetailsService {


    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {
        def portal = SecurityRequestHolder.request.getParameter('portal')

        User user
        User.withTransaction {
            def query = User.where {username == username && portalId == portal as int}

            user = query.find()
        }

        Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
        createUserDetails user, authorities
    }

    protected UserDetails createUserDetails(User user, Collection<GrantedAuthority> authorities) {

        new FieldDataUser(user.username, user.password, user.enabled, authorities, user.id, user.portalId)
    }
}
