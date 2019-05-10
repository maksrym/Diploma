package model;

import enums.Language;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Article> articles;

    @ManyToOne
    private Tag parentTag;

    @OneToMany(mappedBy = "parentTag")
    private List<Tag> childTags;

    @Enumerated(EnumType.STRING)
    private Language language;

    protected Tag() {
        this.articles = new ArrayList<>();
        this.childTags = new ArrayList<>();
    }

    public Tag(String name, Language language) {
        this();
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        this.parentTag = parentTag;
    }

    public List<Tag> getChildTag() {
        return childTags;
    }

    public void setChildTag(ArrayList<Tag> childTags) {
        this.childTags = childTags;
    }

    public void addChildTag(Tag child) {
        childTags.add(child);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
