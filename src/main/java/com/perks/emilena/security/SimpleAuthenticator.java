package com.perks.emilena.security;

import com.perks.emilena.api.SystemUser;
import com.perks.emilena.dao.SystemUserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, SystemUser> {

    private final SystemUserDAO systemUserDAO;

    public SimpleAuthenticator(SystemUserDAO systemUserDAO) {
        this.systemUserDAO = systemUserDAO;
    }


    @Override
    public Optional<SystemUser> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        SystemUser systemUser =
                systemUserDAO.findByUserName(basicCredentials.getUsername());
        if (systemUser == null || systemUser.getPassword() == null) {
            return Optional.empty();
        }

        if (systemUser.getPassword().equalsIgnoreCase(basicCredentials.getPassword())) {
            return Optional.of(systemUser);
        }

        return Optional.empty();
    }
}
