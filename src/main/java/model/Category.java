package model;

import enums.Language;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tags")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childCategories;

    @Enumerated(EnumType.STRING)
    private Language language;

    protected Category() {
        this.articles = new ArrayList<>();
        this.childCategories = new ArrayList<>();
    }

    public Category(String name, Language language) {
        this();
        this.name = name;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubcategory() {
        return childCategories;
    }

    public void setChildTag(ArrayList<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public void addChildTag(Category child) {
        childCategories.add(child);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
