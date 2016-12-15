package com.perks.emilena.service;

import com.perks.emilena.api.Staff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Emilena project copyright - 2016
 * Created by Geoff Perks.
 * Date: 10/12/2016
 */
@RunWith(MockitoJUnitRunner.class)
public class RotaItemServiceTest {

    @Test
    public void shouldSelectRandomIndex() {

        Staff s1 = new Staff();
        Staff s2 = new Staff();
        Staff s3 = new Staff();

        List<Staff> staffList = new ArrayList<>();
        staffList.add(s1);
        staffList.add(s2);
        staffList.add(s3);

        Random random = new Random();
        int i = random.nextInt(staffList.size());

        System.out.println(i);

    }

}