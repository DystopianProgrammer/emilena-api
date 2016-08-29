package com.perks.emilena.resource;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Availability;
import com.perks.emilena.dao.AvailabilityDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 29/08/2016.
 */
public class AvailabilityResourceTest {

    private static final AvailabilityDAO dao = mock(AvailabilityDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new AvailabilityResource(dao)).build();

    @Before
    public void setUp() throws Exception {
        when(dao.findAll()).thenReturn(Lists.newArrayList(new Availability()));
    }

    @After
    public void tearDown() throws Exception {
        reset(dao);
    }

    @Test
    public void findAll() throws Exception {
        assertThat(resources.client().target("/availability")
                .request()
                .get()).isNotNull();
        verify(dao, times(1)).findAll();
    }

}