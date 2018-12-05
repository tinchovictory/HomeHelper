package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentHibernateDao implements AppointmentDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Appointment> getAppointmentsByProviderId(int providerId) {
        return em.createQuery("from Appointment as a where a.provider.id = :providerid", Appointment.class)
                .setParameter("providerid", providerId)
                .getResultList();
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(int userId) {
        return em.createQuery("from Appointment as a where a.client.id = :userid", Appointment.class)
                .setParameter("userid", userId)
                .getResultList();
    }

    @Override
    public Optional<Appointment> getAppointment(int appointmentId) {
        return Optional.ofNullable(em.find(Appointment.class, appointmentId));
    }

    @Override
    public Optional<Appointment> addAppointment(int clientId, int providerId, int serviceTypeId, Date date, String address, String jobDescripcion) {
        Optional<User> user = Optional.ofNullable(em.find(User.class, clientId));
        if (!user.isPresent()) {
            return Optional.empty();
        }
        Optional<SProvider> provider = Optional.ofNullable(em.find(SProvider.class, providerId));
        if (!provider.isPresent()) {
            return Optional.empty();
        }
        Optional<ServiceType> serviceType = Optional.ofNullable(em.find(ServiceType.class, serviceTypeId));
        if (!serviceType.isPresent()) {
            return Optional.empty();
        }
        Appointment appointment = new Appointment(user.get(), provider.get(), serviceType.get(), date, address, Status.Pending, jobDescripcion, false);
        em.persist(appointment);
        return Optional.ofNullable(appointment);
    }

    @Override
    public boolean updateStatusOfAppointment(int appointmentId, Status status) {
        Optional<Appointment> appointmentOp = Optional.ofNullable(em.find(Appointment.class, appointmentId));


        appointmentOp.ifPresent(appointment -> appointment.setStatus(status));
        return appointmentOp.isPresent();
    }

    @Override
    public boolean updateDateOfAppointment(int appointmentId, Date date) {
        Optional<Appointment> appointment = Optional.ofNullable(em.find(Appointment.class, appointmentId));
        if (!appointment.isPresent()) {
            return false;
        }
        appointment.get().setDate(date);
        return true;
    }

    @Override
    public boolean removeAppointment(int appointmentId) {
        Optional<Appointment> appointmentOp = Optional.ofNullable(em.find(Appointment.class, appointmentId));
        appointmentOp.ifPresent(appointment -> em.remove(appointment));
        return appointmentOp.isPresent();
    }

    @Override
    public boolean reviewAppointment(int appointmentId, int userId, int aptitudeId, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {
        Optional<Appointment> appointment = Optional.ofNullable(em.find(Appointment.class, appointmentId));
        if (!appointment.isPresent()) {
            return false;
        }
        Optional<User> user = Optional.ofNullable(em.find(User.class, userId));
        if (!user.isPresent()) {
            return false;
        }
        Optional<Aptitude> aptitude = Optional.ofNullable(em.find(Aptitude.class, aptitudeId));
        if (!aptitude.isPresent()) {
            return false;
        }
        Review review = new Review(quality, cleanness, price, punctuality, treatment, comment, Date.from(Instant.now()), user.get(), aptitude.get());
        em.persist(review);
        appointment.get().setClientReview(true);
        return true;
    }
}
