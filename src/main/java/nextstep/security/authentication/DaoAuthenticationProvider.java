package nextstep.security.authentication;

import nextstep.app.ui.AuthenticationException;
import nextstep.security.userdetails.UserDetails;
import nextstep.security.userdetails.UserDetailsService;

public class DaoAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BasicTokenAuthenticationConverter basicTokenAuthenticationConverter = new BasicTokenAuthenticationConverter();

    public DaoAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BasicTokenAuthentication.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        Authentication token = basicTokenAuthenticationConverter.convert((BasicTokenAuthentication) authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(token.getPrincipal().toString());
        if (!userDetails.getPassword().equals(token.getCredentials())) {
            throw new AuthenticationException();
        }
        return new AuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
    }
}
