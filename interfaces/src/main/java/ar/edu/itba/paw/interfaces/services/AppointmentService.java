package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Status;

import java.sql.Timestamp;
import java.util.List;

public interface AppointmentService {

    List<Appointment> getAppointmentsByProviderId(int providerId);

    List<Appointment> getAppointmentsByUserId(int userId);

    List<Appointment> getAppointmentsByProviderId(int providerId, Status status);

    List<Appointment> getAppointmentsByUserId(int userId, Status status);

    Appointment getAppointment(int appointmentId);

    Integer getAppointmentId(int clientId, int providerId, Timestamp date, String address);

    boolean addAppointment(int clientId, int providerId, int serviceTypeId, Timestamp date, String address, String jobDescripcion);

    boolean confirmAppointment(int appointmentId);

    boolean completedAppointment(int appointmentId);

}