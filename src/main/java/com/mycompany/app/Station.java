package com.mycompany.app;

import java.io.Serializable;

public class Station implements Serializable{
    int station_id;
    int bikes_available;
    int docks_available;
    String date_time;

    public Station(int station_id, int bikes_available, int docks_available, String date_time) {
        this.station_id = station_id;
        this.bikes_available = bikes_available;
        this.docks_available = docks_available;
        this.date_time = date_time;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getBikes_available() {
        return bikes_available;
    }

    public void setBikes_available(int bikes_available) {
        this.bikes_available = bikes_available;
    }

    public int getDocks_available() {
        return docks_available;
    }

    public void setDocks_available(int docks_available) {
        this.docks_available = docks_available;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String toString() {
        return station_id + "," + bikes_available + "," +  docks_available + "," + date_time;
    }
}
