package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointments_appointmentid_seq")
    @SequenceGenerator(sequenceName = "appointments_appointmentid_seq", name = "appointments_appointmentid_seq", allocationSize = 1)
    @Column(name = "appointmentid")
    private int appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "providerid", referencedColumnName = "userid", nullable = false)
    private SProvider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicetypeid", nullable = false)
    private ServiceType serviceType;

    @Column(name = "appointmentDate")
    private Date date;

    @Column(name = "address", length =  10000, nullable = false)
    private String address;

    @Column(name = "status", length =  20, nullable = false)
    private String status;

    @Column(name = "jobDescription", length =  10000, nullable = false)
    private String jobDescripcion;

    @Column(name = "clientReview")
    private boolean clientReview;

    /* package */ Appointment(){

    }
    public Appointment(User client, SProvider provider, ServiceType serviceType, Date date, String address, Status estatus, String jobDescripcion,boolean clientReview) {
        this.client = client;
        this.provider = provider;
        this.serviceType = serviceType;
        this.date = date;
        this.address = address;
        this.status = estatus.toString();
        this.jobDescripcion = jobDescripcion;
        this.clientReview = clientReview;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public User getClient() {
        return client;
    }

    public SProvider getProvider() {
        return provider;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Date getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public Status getStatus() {
        return Status.getStatus(status);
    }

    public String getJobDescripcion() {
        return jobDescripcion;
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public boolean isClientReview() {
        return clientReview;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setClientReview(boolean clientReview) {
        this.clientReview = clientReview;
    }

}
