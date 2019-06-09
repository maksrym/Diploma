package dto;

import enums.Language;
import model.Article;
import model.Category;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewArticleDTO {
    private String title;
    private String abbreviation;
    private String text;
    private Language language;
    private Long pageId;
    private List<CategoryDTO> categories;
    private transient DateTime uploadTime;

    protected NewArticleDTO() {
        categories = new ArrayList<>();
        uploadTime = new DateTime();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public DateTime getUploadTime() {
        return uploadTime;
    }
}

