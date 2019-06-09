package dto;

import enums.Language;
import model.Category;

public class CategoryDTO {
    private String name;
    private Long id;
    private CategoryDTO rootCategory;
    private Language language;

    protected CategoryDTO() {

    }

    public CategoryDTO(Category category) {
        this.name = category.getName();
        this.id = category.getId();
        this.language = category.getLanguage();

        if(category.getParentCategory() != null) {
            rootCategory = new CategoryDTO(category.getParentCategory());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public CategoryDTO getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(CategoryDTO rootCategory) {
        this.rootCategory = rootCategory;
    }
}
