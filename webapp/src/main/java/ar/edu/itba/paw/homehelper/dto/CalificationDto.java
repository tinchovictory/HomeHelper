package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Aptitude;

public class CalificationDto {
    private double quality;
    private double cleanness;
    private double price;
    private double punctuality;
    private double treatement;
    private double general;

    public CalificationDto() {
    }

    public CalificationDto(Aptitude aptitude) {
        this.quality = aptitude.getQualityCalification();
        this.cleanness = aptitude.getCleannessCalification();
        this.price = aptitude.getPriceCalification();
        this.punctuality = aptitude.getPunctualityCalification();
        this.treatement = aptitude.getTreatmentCalification();
        this.general = aptitude.getGeneralCalification();
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getCleanness() {
        return cleanness;
    }

    public void setCleanness(double cleanness) {
        this.cleanness = cleanness;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(double punctuality) {
        this.punctuality = punctuality;
    }

    public double getTreatement() {
        return treatement;
    }

    public void setTreatement(double treatement) {
        this.treatement = treatement;
    }

    public double getGeneral() {
        return general;
    }

    public void setGeneral(double general) {
        this.general = general;
    }
}