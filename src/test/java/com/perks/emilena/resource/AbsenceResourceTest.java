package com.perks.emilena.resource;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Absence;
import com.perks.emilena.dao.AbsenceDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 29/08/2016.
 */
public class AbsenceResourceTest {


    private static final AbsenceDAO absenceDAO = mock(AbsenceDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new AbsenceResource(absenceDAO))
            .build();

    @Before
    public void setUp() throws Exception {
        when(absenceDAO.findAll()).thenReturn(Lists.newArrayList(new Absence()));
    }

    @After
    public void tearDown() throws Exception {
        reset(absenceDAO);
    }

    @Test
    public void testGetAbsences() {
        assertThat(resources.client().target("/absence/all").request().get(List.class));
        verify(absenceDAO, times(1)).findAll();
    }

}