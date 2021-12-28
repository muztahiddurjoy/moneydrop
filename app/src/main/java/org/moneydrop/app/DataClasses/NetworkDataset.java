package org.moneydrop.app.DataClasses;

public class NetworkDataset {
    String country_code;
    String country_name;
    String city;
    String postal;
    String latitude;
    String longitude;
    String IPv4;
    String state;

    public NetworkDataset() {

    }

    public NetworkDataset(String country_code, String country_name, String city, String postal, String latitude, String longitude, String IPv4, String state) {
        this.country_code = country_code;
        this.country_name = country_name;
        this.city = city;
        this.postal = postal;
        this.latitude = latitude;
        this.longitude = longitude;
        this.IPv4 = IPv4;
        this.state = state;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIPv4() {
        return IPv4;
    }

    public void setIPv4(String IPv4) {
        this.IPv4 = IPv4;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
