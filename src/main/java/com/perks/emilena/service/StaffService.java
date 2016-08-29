package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Staff;
import com.perks.emilena.api.SystemUser;
import com.perks.emilena.api.type.RoleType;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.dao.SystemUserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
public class StaffService implements PersonService<Staff> {

    private static final Logger logger = LoggerFactory.getLogger(StaffService.class);

    private final StaffDAO staffDAO;
    private final SystemUserDAO systemUserDAO;

    public StaffService(StaffDAO staffDAO, SystemUserDAO systemUserDAO) {
        this.staffDAO = staffDAO;
        this.systemUserDAO = systemUserDAO;
    }

    @Override
    public Staff create(Staff person) {
        person.setActive(true);
        person = staffDAO.create(person);
        Serializable serializable = generateSystemUser(person);
        logger.info("Created staff {} with linked system user id {}", person.getId(), serializable);
        return person;
    }

    private Serializable generateSystemUser(Staff person) {
        SystemUser systemUser = new SystemUser();
        systemUser.setStaff(person);
        systemUser.setUserName(person.getEmail());
        systemUser.setRoleTypes(Lists.newArrayList(RoleType.STAFF));
        systemUser.setPassword("password123");
        return systemUserDAO.create(systemUser);
    }
}
