package com.perks.emilena.security;

import com.google.common.base.Optional;
import com.perks.emilena.api.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {


    /**
     * Given a set of user-provided credentials, return an optional principal.
     * <p/>
     * If the credentials are valid and map to a principal, returns an {@code Optional.of(p)}.
     * <p/>
     * If the credentials are invalid, returns an {@code Optional.absent()}.
     *
     * @param credentials a set of user-provided credentials
     * @return either an authenticated principal or an absent optional
     * @throws AuthenticationException if the credentials cannot be authenticated due to an
     * underlying error
     */
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("secret".equals(credentials.getPassword())) {
            User user = new User();
            user.setUserName(credentials.getUsername());
            return Optional.of(user);
        }
        return Optional.absent();
    }
}
