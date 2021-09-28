package dtos;

import entities.Hobbies;

public class HobbiesDTO {
    private Integer id;
    private String name;
    private int price;

    public HobbiesDTO(Hobbies hobbies){
        if (hobbies.getId() != null)
            this.id = hobbies.getId();
        this.name = hobbies.getName();
        this.price = hobbies.getPrice();

    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
