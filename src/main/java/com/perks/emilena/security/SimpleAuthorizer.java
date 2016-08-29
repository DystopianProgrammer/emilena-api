package com.perks.emilena.security;

import com.perks.emilena.api.SystemUser;
import com.perks.emilena.api.type.RoleType;
import io.dropwizard.auth.Authorizer;

import java.security.Principal;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class SimpleAuthorizer implements Authorizer<SystemUser> {

    /**
     * Decides if access is granted for the given principal in the given role.
     *
     * @param principal a {@link Principal} object, representing a user
     * @param role a user role
     * @return {@code true}, if the access is granted, {@code false otherwise}
     */
    @Override
    public boolean authorize(SystemUser principal, String role) {
        return principal.getRoleTypes()
                .stream()
                .anyMatch(r -> r.equals(RoleType.valueOf(role)));
    }
}
