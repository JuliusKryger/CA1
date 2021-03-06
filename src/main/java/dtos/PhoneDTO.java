package dtos;


import entities.Phone;

import java.util.Objects;

public class PhoneDTO {
    private int number;
    private String description;

    public PhoneDTO() {
    }

    public PhoneDTO(Phone phone) {
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }

    public PhoneDTO(int number) {
        this.number = number;
    }

    public PhoneDTO(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public PhoneDTO(int number, String description, PersonDTO person) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
                "number=" + number +
                ", description='" + description + '\'' +
                '}';
    }
}