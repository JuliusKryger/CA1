package dtos;

import entities.Hobbies;

public class HobbiesDTO {
    private Integer id;
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public HobbiesDTO(Hobbies hobbies){
        if (hobbies.getId() != null)
            this.id = hobbies.getId();
        this.name = hobbies.getName();
        this.wikiLink = hobbies.getWikiLink();
        this.category = hobbies.getCategory();
        this.type = hobbies.getType();
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
