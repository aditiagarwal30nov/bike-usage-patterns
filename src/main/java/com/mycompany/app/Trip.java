package com.mycompany.app;

public class Trip {
    private int id;
    private float duration;
    private String start_date;
    private String start_station_name;
    private int start_station_id;
    private String end_date;
    private String end_station_name;
    private int end_station_id;
    private int bike_id;
    private String subscription_type;
    private int zip_code;

    public Trip(int id, float duration, String start_date, String start_station_name, int start_station_id, String end_date, String end_station_name, int end_station_id, int bike_id, String subscription_type, int zip_code) {
        this.id = id;
        this.duration = duration;
        this.start_date = start_date;
        this.start_station_name = start_station_name;
        this.start_station_id = start_station_id;
        this.end_date = end_date;
        this.end_station_name = end_station_name;
        this.end_station_id = end_station_id;
        this.bike_id = bike_id;
        this.subscription_type = subscription_type;
        this.zip_code = zip_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_station_name() {
        return start_station_name;
    }

    public void setStart_station_name(String start_station_name) {
        this.start_station_name = start_station_name;
    }

    public int getStart_station_id() {
        return start_station_id;
    }

    public void setStart_station_id(int start_station_id) {
        this.start_station_id = start_station_id;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_station_name() {
        return end_station_name;
    }

    public void setEnd_station_name(String end_station_name) {
        this.end_station_name = end_station_name;
    }

    public int getEnd_station_id() {
        return end_station_id;
    }

    public void setEnd_station_id(int end_station_id) {
        this.end_station_id = end_station_id;
    }

    public int getBike_id() {
        return bike_id;
    }

    public void setBike_id(int bike_id) {
        this.bike_id = bike_id;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }
}

