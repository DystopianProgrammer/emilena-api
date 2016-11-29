package com.perks.emilena.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * Created by Geoff Perks
 * Date: 06/08/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {


    AppointmentService appointmentService;

    @Test
    public void testTrimmingOnPostCode() {
        String postCode = "BN3 7FG";
        assertThat(StringUtils.deleteWhitespace(postCode)).matches("BN37FG");
    }

}