package model;

import converter.DateTimeAttributeConverter;
import enums.Language;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;

    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime uploadTime;

    @Convert(converter = DateTimeAttributeConverter.class)
    private DateTime lastChange;

    @ManyToMany
    @JoinTable(
            name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    protected Article() {
        this.tags = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public DateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(DateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public DateTime getLastChange() {
        return lastChange;
    }

    public void setLastChange(DateTime lastChange) {
        this.lastChange = lastChange;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
