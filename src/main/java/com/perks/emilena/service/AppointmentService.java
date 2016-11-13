package com.perks.emilena.service;

import com.google.common.collect.Lists;
import com.perks.emilena.api.*;
import com.perks.emilena.api.type.PersonType;
import com.perks.emilena.dao.AbsenceDAO;
import com.perks.emilena.dao.AvailabilityDAO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Geoff Perks
 * Date: 13/07/2016.
 */
public class AppointmentService {

    private static final int MAX_ROTA_DURATION = 7;

    private final AvailabilityDAO availabilityDAO;
    private final AbsenceDAO absenceDAO;

    public AppointmentService(AvailabilityDAO availabilityDAO, AbsenceDAO absenceDAO) {
        this.availabilityDAO = availabilityDAO;
        this.absenceDAO = absenceDAO;
    }


    /**
     * TODO - fill in all the points
     * <p>
     * The initial sweep, this could be improved with the sorting mechanism
     * perhaps do this with recursion and in parallel chunks - this needs some improvement
     * identify where any client & staff overlaps exist
     *
     * @param date
     * @return
     */
    public List<AppointmentService.Appointment> appointmentsByDate(LocalDate date) {

        // 1. get a list of all absences for the date duration
        List<Absence> absences = getAbsences(date);

        // 2. then create a pairing of staff and clients for an appointment - 1st sweep
        List<AppointmentService.Appointment> appointments = getAppointments();

        // 3. then eliminate appointments where there is an absence
        List<AppointmentService.Appointment> filteredByAbsence = filterByAbsence(absences, appointments);

        // 4. then verify we haven't double allocated either staff or clients

        // 5. then identify all clients without appointments

        // 6. then identify all staff without appointments

        // 7. finally clean and return all appointments


        return filteredByAbsence;
    }

    // takes the unfiltered list of appointments, and all absences
    private List<AppointmentService.Appointment> filterByAbsence(List<Absence> absences, List<AppointmentService.Appointment> appointments) {
        if (absences == null || absences.isEmpty()) {
            return Lists.newArrayList(appointments);
        }

        List<AppointmentService.Appointment> filtered = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++) {
            boolean isAbsent = false;
            for (int k = 0; k < absences.size(); k++) {
                isAbsent = (absences.get(k).getDate().getDayOfWeek().equals(appointments.get(i).getDay()) &&
                        isClientOrStaffAbsent(appointments.get(i), absences.get(k)));
            }
            if (!isAbsent) {
                filtered.add(appointments.get(i));
            }
        }
        return filtered;
    }

    // A simple check to see whether it's client or staff who is absent
    private boolean isClientOrStaffAbsent(AppointmentService.Appointment appointment, Absence absence) {
        Person person = absence.getPerson();
        if (person instanceof Client) {
            return (appointment.getClient().equals(person));
        } else {
            return appointment.getStaff().equals(person);
        }
    }

    // Get all the absences and filter them from the date specified
    // up until 7 days from the date specified. This prevents us from eagerly
    // eliminating any appointments unnecessarily
    private List<Absence> getAbsences(LocalDate date) {

        Predicate<Absence> hasAbsence = (absence) ->
                Period.between(absence.getDate(), date).getDays() < MAX_ROTA_DURATION;

        return this.absenceDAO.findAll().stream()
                .filter(hasAbsence)
                .collect(Collectors.toList());
    }

    // if we have availabilities - which we should!
    // for each availability, compare with all other availabilities to determine
    // if there is an overlap. If there is create a temporary appointment
    private List<AppointmentService.Appointment> getAppointments() {

        List<AppointmentService.Appointment> appointments = new ArrayList<>();
        List<Availability> availabilities = availabilityDAO.findAll();

        for (int i = 0; i < availabilities.size() - 1; i++) {
            for (int k = i + 1; k < availabilities.size(); k++) {
                Availability availability = availabilities.get(i);
                Availability other = availabilities.get(k);
                if (availability.compareTo(other) == 0) {
                    if (availability.getPerson() instanceof Staff) {
                        AppointmentService.Appointment appointment = new AppointmentService.Appointment((Staff) availability.getPerson(),
                                (Client) other.getPerson(), other.getDayOfWeek(),
                                other.getFromTime(), other.getToTime());
                        appointments.add(appointment);
                    } else {
                        AppointmentService.Appointment appointment = new AppointmentService.Appointment((Staff) other.getPerson(),
                                (Client) availability.getPerson(), availability.getDayOfWeek(),
                                availability.getFromTime(), availability.getToTime());
                        appointments.add(appointment);
                    }
                }
            }
        }

        return appointments;
    }

    // any appointment will consist of a client and a staff who have overlapping availability times
    // from and up to 7 days from the start date the rota was created
    private List<AppointmentService.AbsenceMatch> absentMatch(AppointmentService.Appointment appointment, Absence absence) {

        List<AppointmentService.AbsenceMatch> clientAbsences = appointment.getClient().getAvailabilities().stream()
                .filter(availability -> availability.getDayOfWeek().equals(absence.getDate().getDayOfWeek()))
                .map(availability -> new AppointmentService.AbsenceMatch(PersonType.CLIENT, appointment))
                .collect(Collectors.toList());

        List<AppointmentService.AbsenceMatch> staffAbsences = appointment.getStaff().getAvailabilities().stream()
                .filter(availability -> availability.getDayOfWeek().equals(absence.getDate().getDayOfWeek()))
                .map(availability -> new AppointmentService.AbsenceMatch(PersonType.STAFF, appointment))
                .collect(Collectors.toList());

        List<AppointmentService.AbsenceMatch> absences = new ArrayList<>();
        absences.addAll(clientAbsences);
        absences.addAll(staffAbsences);

        return absences;
    }

    private static class AbsenceMatch {

        // the person type who is absent on that day
        private final PersonType personType;
        private final AppointmentService.Appointment appointment;

        public AbsenceMatch(PersonType personType, AppointmentService.Appointment appointment) {
            this.personType = personType;
            this.appointment = appointment;
        }

        public PersonType getPersonType() {
            return personType;
        }

        public AppointmentService.Appointment getAppointment() {
            return appointment;
        }
    }

    public static class Appointment {

        private final Staff staff;
        private final Client client;
        private final DayOfWeek day;
        private final LocalTime start;
        private final LocalTime finish;

        public Appointment(Staff staff, Client client, DayOfWeek day, LocalTime start, LocalTime finish) {
            this.staff = staff;
            this.client = client;
            this.day = day;
            this.start = start;
            this.finish = finish;
        }

        public Staff getStaff() {
            return staff;
        }

        public Client getClient() {
            return client;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public LocalTime getStart() {
            return start;
        }

        public LocalTime getFinish() {
            return finish;
        }
    }

}
