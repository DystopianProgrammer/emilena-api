package com.perks.emilena.security;

import com.perks.emilena.api.SystemUser;
import com.perks.emilena.dao.SystemUserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {

    private final SystemUserDAO systemUserDAO;

    public SimpleAuthenticator(SystemUserDAO systemUserDAO) {
        this.systemUserDAO = systemUserDAO;
    }


    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {

        SystemUser systemUser = systemUserDAO.findByUserName(basicCredentials.getUsername());

        if (systemUser == null || StringUtils.isBlank(systemUser.getPassword())) {
            Optional.empty();
        } else {
            if (systemUser.getPassword().equals(basicCredentials.getPassword())) {
                User user = new User();
                user.setUserName(systemUser.getUserName());
                user.setStaff(systemUser.getStaff());
                user.setRoles(systemUser.getRoles()
                        .stream()
                        .map(role -> role.getRoleType())
                        .collect(Collectors.toList()));

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
