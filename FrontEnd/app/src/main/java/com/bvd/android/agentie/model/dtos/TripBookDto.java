package com.bvd.android.agentie.model.dtos;

/**
 * Created by bara on 2/2/2018.
 */

public class TripBookDto {
    private Integer id;
    private Integer rooms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public TripBookDto(Integer id, Integer rooms) {

        this.id = id;
        this.rooms = rooms;
    }
}
