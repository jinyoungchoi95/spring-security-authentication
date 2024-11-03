package nextstep.security.authentication;

import nextstep.app.ui.AuthenticationException;

import java.util.List;

public class ProviderManager implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;

    public ProviderManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    public ProviderManager(AuthenticationProvider... providers) {
        this(List.of(providers));
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (providers.isEmpty()) {
            return authentication;
        }
        return authenticate(authentication, 0);
    }

    private Authentication authenticate(Authentication authentication, int index) {
        if (index == providers.size()) {
            throw new AuthenticationException();
        }
        AuthenticationProvider provider = providers.get(index);
        if (provider.supports(authentication.getClass())) {
            return provider.authenticate(authentication);
        }
        return authenticate(authentication, index + 1);
    }
}
