package com.perks.emilena.security;

import com.perks.emilena.api.Role;
import com.perks.emilena.api.type.RoleType;
import io.dropwizard.auth.Authorizer;

import java.security.Principal;
import java.util.List;

/**
 * Created by Geoff Perks
 * Date: 28/07/2016.
 */
public class SimpleAuthorizer implements Authorizer<User> {

    /**
     * Decides if access is granted for the given principal in the given role.
     *
     * @param principal a {@link Principal} object, representing a user
     * @param role a user role
     * @return {@code true}, if the access is granted, {@code false otherwise}
     */
    @Override
    public boolean authorize(User principal, String role) {
        return principal.getRoles()
                .stream()
                .anyMatch(r -> r.getRoleType() == RoleType.valueOf(role));
    }
}
