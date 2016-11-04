package com.perks.emilena.resource;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.StaffDAO;
import com.perks.emilena.service.StaffService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by Geoff Perks
 * Date: 29/08/2016.
 */
public class StaffResourceTest {


    private static final StaffService service = mock(StaffService.class);
    private static final StaffDAO dao = mock(StaffDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new StaffResource(service, dao)).build();

    @Before
    public void setUp() {

        Staff staff = new Staff();
        staff.setForename("Bob");
        staff.setSurname("Smith");

        Client client = new Client();
        client.setForename("Dave");
        client.setSurname("Jones");

        when(service.create(any(Staff.class))).thenReturn(staff);
        when(dao.findById(any(Long.class))).thenReturn(staff);
        when(dao.findAllActive()).thenReturn(Lists.newArrayList(staff));
        when(dao.clientsByStaffId(any(Long.class))).thenReturn(Lists.newArrayList(client));
        when(dao.update(any(Staff.class))).thenReturn(staff);
        when(dao.update(any(Staff.class))).thenReturn(staff);
    }

    @After
    public void tearDown() {
        reset(service, dao);
    }

    @Test
    public void findPerson() throws Exception {
        assertThat(resources.client().target("/staff/1")
                .request().get().readEntity(Staff.class)).isNotNull();

        verify(dao, times(1)).findById(1L);
    }

    @Test
    public void findAll() throws Exception {
        assertThat(resources.client().target("/staff/all").request().get().getStatus()).isEqualTo(200);
    }

    @Test
    public void add() throws Exception {
        Staff staff = new Staff();
        staff.setForename("Bob");
        staff.setSurname("Smith");

        Staff entity = resources.client().target("/staff/add")
                .request()
                .post(Entity.entity(staff, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Staff.class);

        assertThat(entity.getForename()).isEqualTo("Bob");
        assertThat(entity.getSurname()).isEqualTo("Smith");

        verify(service, times(1)).create(staff);
    }

    @Test
    public void update() throws Exception {
        Staff staff = new Staff();
        staff.setForename("Bob");
        staff.setSurname("Smith");

        assertThat(resources.client().target("/staff/update")
                .request()
                .post(Entity.entity(staff, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Staff.class))
                .isNotNull();

        verify(dao, times(1)).update(staff);
    }

    @Test
    public void clientsFromStaffId() throws Exception {
        assertThat(resources.client().target("/staff/staff/clients/1").request().get().getStatus()).isEqualTo(200);
    }

    @Test
    public void findAllActive() throws Exception {
        assertThat(resources.client().target("/staff/active").request().get().getStatus()).isEqualTo(200);
    }

}