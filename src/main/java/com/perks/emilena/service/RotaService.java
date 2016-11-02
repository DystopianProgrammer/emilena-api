package com.perks.emilena.service;

import com.perks.emilena.api.Assignment;
import com.perks.emilena.api.Availability;
import com.perks.emilena.api.Client;
import com.perks.emilena.api.Rota;
import com.perks.emilena.api.Staff;
import com.perks.emilena.dao.ClientDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 06/09/2016.
 */
public class RotaService {

    private static final Logger logger = LoggerFactory.getLogger(RotaService.class);

    private final ClientDAO clientDAO;

    public RotaService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Rota rotaForWeek(LocalDateTime localDateTime) {

        List<Client> clients = this.clientDAO.findAllActive();

        List<Assignment> monday = new ArrayList<>();
        List<Assignment> tuesday = new ArrayList<>();
        List<Assignment> wednesday = new ArrayList<>();
        List<Assignment> thursday = new ArrayList<>();
        List<Assignment> friday = new ArrayList<>();
        List<Assignment> saturday = new ArrayList<>();
        List<Assignment> sunday = new ArrayList<>();

        Rota rota = new Rota();
        rota.setWeekCommencing(localDateTime);

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.MONDAY).ifPresent(monday::add);
            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.MONDAY);
            }
        });

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.TUESDAY).ifPresent(tuesday::add);

            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.TUESDAY);
            }
        });

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.WEDNESDAY).ifPresent(wednesday::add);

            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.WEDNESDAY);
            }
        });

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.THURSDAY).ifPresent(thursday::add);

            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.THURSDAY);
            }
        });

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.FRIDAY).ifPresent(friday::add);

            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.FRIDAY);
            }
        });

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.SATURDAY).ifPresent(saturday::add);

            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.SATURDAY);
            }
        });

        clients.stream().forEach(client -> {
            try {
                this.forDay(client, DayOfWeek.SUNDAY).ifPresent(sunday::add);

            } catch (RuntimeException e) {
                logger.warn("Could not allocate staff for client {} on {}", client.getId(), DayOfWeek.SUNDAY);
            }
        });

        rota.setMonday(monday);
        rota.setTuesday(tuesday);
        rota.setWednesday(wednesday);
        rota.setThursday(thursday);
        rota.setFriday(friday);
        rota.setSaturday(saturday);
        rota.setSunday(sunday);

        return rota;
    }


    /**
     * Creates a single assignment for a given day of the week
     * for a given client with a given preferred member of staff
     *
     * @param client - the client for a given day at a given time
     * @param day - the specified day for the rota
     * @return an Optional of Assignment
     */
    public Optional<Assignment> forDay(Client client, DayOfWeek day) {

        // preconditions
        if (client == null || day == null || client.getAvailabilities() == null) {
            return Optional.empty();
        }

        Assignment assignment = client.getAvailabilities().stream()
                // first filter the clients to where we have a day match
                .filter(a -> day.equals(a.getDayOfWeek()))
                // then map a potential Assignment, and store all the clients preferences on this object
                .map(a -> {
                    Assignment potentialAssignment = new Assignment();
                    potentialAssignment.setClient(client);
                    potentialAssignment.setHours(Duration.between(a.getFromTime(), a.getToTime()).toHours());
                    potentialAssignment.setDayOfWeek(a.getDayOfWeek());
                    potentialAssignment.setTimeFrom(a.getFromTime());
                    potentialAssignment.setTimeTo(a.getToTime());
                    return potentialAssignment;
                })
                // in this optional we have a potential assignment, so then
                // check that the preferred staff on this potential assignment for this client is available
                // on the same day, for the clients preferable hours
                .findAny()
                .orElse(new Assignment());


        // if we have a potential client appointment for this day, then check if any
        // preferred staff also work on this day
        if (assignment != null && assignment.getClient() != null && assignment.getClient().getStaff() != null) {
            Availability match = assignment.getClient().getStaff().stream()
                    .filter(staff -> staff.getAvailabilities() != null)
                    .flatMap(staff -> staff.getAvailabilities().stream())
                    .filter(a -> day.equals(a.getDayOfWeek()))
                    .collect(Collectors.toSet())
                    .stream()
                    // now we have a collection of potential staff assignments
                    // check if the time overlaps for a match
                    .filter(a -> {
                        if (assignment.getTimeFrom().equals(a.getFromTime()) &&
                                assignment.getTimeTo().equals(a.getToTime())) {
                            return true;
                        }
                        if (assignment.getTimeFrom().isAfter(a.getFromTime()) &&
                                assignment.getTimeTo().equals(a.getToTime())) {
                            return true;
                        }
                        if (assignment.getTimeFrom().isAfter(a.getFromTime()) &&
                                assignment.getTimeTo().isBefore(a.getToTime())) {
                            return true;
                        }
                        return false;
                    })
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("No available staff"));

            // With the ability to create manual appointments, this may lead to a conflict.
            // TODO Validation is required that we're not double booking.
            // assignment.setStaff((Staff) match.getPerson());
        }

        // post conditions
        if (assignment == null || assignment.getStaff() == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(assignment);
    }

}
