package nextstep.security.authentication;

import nextstep.app.ui.AuthenticationException;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicTokenAuthenticationConverter {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("^Basic (.+)$");
    private static final String TOKEN_SPLIT_REGEX = ":";
    private static final int TOKEN_LENGTH = 2;
    private static final int TOKEN_EMAIL_INDEX = 0;
    private static final int TOKEN_PASSWORD_INDEX = 1;

    private static String[] parseToken(String authorizationHeader) {
        Matcher matcher = TOKEN_PATTERN.matcher(authorizationHeader);
        if (!matcher.matches()) {
            throw new AuthenticationException();
        }

        String base64Credentials = matcher.group(1);
        String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials));
        return decodedCredentials.split(TOKEN_SPLIT_REGEX, TOKEN_LENGTH);
    }

    public Authentication convert(BasicTokenAuthentication authentication) {
        if (authentication.getToken() == null) {
            throw new AuthenticationException();
        }
        String[] parts = parseToken(authentication.getToken());
        if (parts.length != TOKEN_LENGTH) {
            throw new AuthenticationException();
        }
        return new AuthenticationToken(parts[TOKEN_EMAIL_INDEX], parts[TOKEN_PASSWORD_INDEX]);
    }
}
