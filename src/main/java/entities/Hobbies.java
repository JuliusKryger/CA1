package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Hobbies implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    private String wikiLink;
    private String category;
    private String type;

    public Hobbies(){

    }

    public Hobbies(String name,String wikiLink, String category, String type){
        this.name = name;
        this.wikiLink = wikiLink;
        this.category = category;
        this.type = type;
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
