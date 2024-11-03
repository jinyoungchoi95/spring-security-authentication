package nextstep.security.authentication;

public interface AuthenticationProvider {

    boolean supports(Class<?> authentication);

    Authentication authenticate(Authentication authentication);
}
