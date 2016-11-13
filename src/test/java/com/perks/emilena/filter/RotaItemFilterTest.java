package com.perks.emilena.filter;

import com.perks.emilena.api.Client;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.RotaItem;
import com.perks.emilena.api.Staff;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class RotaItemFilterTest {

    @Test
    public void shouldRemoveDuplicatesForClient() {

        // given
        Client client = new Client();
        client.setId(1L);

        LocalTime start = LocalTime.of(9, 0);
        LocalTime finish = LocalTime.of(10, 0);

        RotaItem item1 = build(DayOfWeek.MONDAY, start, finish, client, new Staff());
        RotaItem item2 = build(DayOfWeek.TUESDAY, start, finish, client, new Staff());
        RotaItem duplicate = item1;

        Rota rota = new Rota();
        rota.setRotaItems(Arrays.asList(item1, duplicate, item2));

        // assert unfiltered
        assertThat(rota.getRotaItems().size()).isEqualTo(3);

        RotaItemFilter filter = new RotaItemFilter();
        Rota filtered = filter.removeDuplicates(rota);

        // assert filtered
        assertThat(filtered.getRotaItems().size()).isEqualTo(2);
    }

    private RotaItem build(DayOfWeek dayOfWeek, LocalTime start, LocalTime finish, Client client, Staff staff) {
        RotaItem rotaItem = new RotaItem();
        rotaItem.setDayOfWeek(dayOfWeek);
        rotaItem.setStart(start);
        rotaItem.setFinish(finish);
        rotaItem.setClient(client);
        rotaItem.setStaff(staff);

        return rotaItem;
    }

}