package com.perks.emilena.security;

import com.perks.emilena.api.SystemUser;
import com.perks.emilena.dao.SystemUserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.apache.commons.lang3.StringUtils;

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

        // we do this to prevent server-side local cache from throwing hibernate exceptions when attempting to
        // fetch relationship tables - this is due to the datastore session being closed by this point.
        systemUser.setStaff(null);

        if (systemUser == null || StringUtils.isBlank(systemUser.getPassword())) {
            return Optional.empty();
        }
        return Optional.of(systemUser);
    }
}
