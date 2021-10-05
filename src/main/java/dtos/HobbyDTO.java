package dtos;

import entities.Hobby;

import java.util.Objects;

public class HobbyDTO {
    private Integer id;
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public HobbyDTO() {
    }

    public HobbyDTO(Hobby entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.wikiLink = entity.getWikiLink();
        this.category = entity.getCategory();
        this.type = entity.getType();
    }

    public HobbyDTO(String name, String wikiLink, String category, String type) {
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
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

    @Override
    public String toString() {
        return "HobbyDTO{" +
                "name='" + name + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
