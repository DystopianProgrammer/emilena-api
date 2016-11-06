package com.perks.emilena.resource;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Client;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.service.ClientService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Geoff Perks
 * Date: 29/08/2016.
 */
public class ClientResourceTest {

    private static final ClientDAO dao = mock(ClientDAO.class);
    private static final ClientService service = mock(ClientService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ClientResource(service, dao)).build();

    @Before
    public void setUp() {

        Client client = new Client();
        client.setForename("Bob");
        client.setSurname("Smith");

        when(dao.findAll()).thenReturn(Lists.newArrayList(client));
        when(dao.create(any(Client.class))).thenReturn(client);
        when(dao.findAllActive()).thenReturn(Lists.newArrayList(client));
        when(dao.findById(any(Long.class))).thenReturn(client);
        when(dao.update(any(Client.class))).thenReturn(client);
        when(service.create(any(Client.class))).thenReturn(client);
    }

    @After
    public void tearDown() {
        reset(service, dao);
    }

    @Test
    public void findAll() throws Exception {

        assertThat(resources.client()
                .target("/client/all")
                .request()
                .get()
                .getStatus()).isEqualTo(200);

        verify(dao, times(1)).findAll();
    }

    @Test
    public void add() throws Exception {

        Client client = new Client();
        client.setForename("Bob");
        client.setSurname("Smith");

        assertThat(resources.client().target("/client/add")
                .request()
                .post(Entity.entity(client, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Client.class))
                .isEqualTo(client);

        verify(service, times(1)).create(any(Client.class));
    }

    @Test
    public void update() throws Exception {

        Client client = new Client();
        client.setForename("Bob");
        client.setSurname("Smith");

        assertThat(resources.client().target("/client/update")
                .request()
                .post(Entity.entity(client, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Client.class))
                .isEqualTo(client);

        verify(dao, times(1)).update(any(Client.class));
    }

    @Test
    public void findPerson() throws Exception {
        assertThat(resources.client()
                .target("/client/1")
                .request()
                .get()
                .readEntity(Client.class)).isNotNull();

        verify(dao, times(1)).findById(1L);
    }

    @Test
    public void findAllActive() throws Exception {
        assertThat(resources.client()
                .target("/client/active")
                .request()
                .get()
                .getStatus()).isEqualTo(200);

        verify(dao, times(1)).findAllActive();
    }

}