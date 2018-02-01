package com.bvd.android.agentie.model;

import java.io.Serializable;

/**
 * Created by bara on 2/1/2018.
 */

public class Trip implements Serializable {
    private Integer id;
    private String name;
    private Integer rooms;
    private String type;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Trip(Integer id, String name, Integer rooms, String type, String status) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
        this.type = type;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String toString1() {
        return rooms + " | " +
                type + " | " +
                status;

    }
}
