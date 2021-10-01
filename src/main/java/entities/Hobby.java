package entities;

import javax.persistence.*;

@Table(name = "hobby")
@Entity
public class Hobby {
    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public Hobby(){

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
}