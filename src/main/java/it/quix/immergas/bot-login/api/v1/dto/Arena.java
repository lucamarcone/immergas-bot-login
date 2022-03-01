package it.quix.immergas.bot-login.api.v1.dto;

import lombok.Data;

@Data
public class Arena {

    public Arena() { }

    public Arena(String name, Integer seats) {
        this.name = name;
        this.seats = seats;
    }

    private String name;
    private Integer seats;

}
