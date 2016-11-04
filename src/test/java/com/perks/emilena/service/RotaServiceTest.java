package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.Assignment;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import com.perks.emilena.dao.StaffDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class RotaServiceTest {

    @Mock
    private ClientDAO clientDAO;

    @Mock
    private StaffDAO staffDAO;

    @InjectMocks
    private RotaService rotaService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(clientDAO, staffDAO);
    }

    @Test
    public void testRotaGeneration() {

    }



}