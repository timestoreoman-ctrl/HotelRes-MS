package com.hotelres.booking_service.dto;

public class RoomDTO {

    private Long id;
    private Boolean available;

    public Long getId() {
        return id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
