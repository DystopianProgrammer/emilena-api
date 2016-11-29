package com.perks.emilena.service;

import com.perks.emilena.dao.RotaDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class RotaServiceTest {

    @Mock
    private RotaItemService rotaItemService;
    @Mock
    private RotaDAO rotaDAO;
    @InjectMocks
    RotaService rotaService;


    @Test
    public void testWeekGeneration() {
        List<LocalDate> weeks = rotaService.weeks();
        assertThat(weeks).isNotNull();
    }
}