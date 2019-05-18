package dto;

import enums.Language;
import model.Article;
import model.Page;
import model.Tag;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDTO {
    private String title;
    private String text;
    private Language language;
    private Map<String, String> links;
    private List<Tag> tags;
    private DateTime uploadTime;

    protected ArticleDTO() {
        tags = new ArrayList<>();
        links = new HashMap<>();
    }

    public ArticleDTO(Article article) {
        this();

        this.title = article.getTitle();
        this.text = article.getText();
        this.language = article.getLanguage();
        this.tags = article.getTags();
        this.uploadTime = article.getUploadTime();

        for(Article anotherArticle : article.getPage().getArticles()) {
            String language = anotherArticle.getLanguage().toString();
            String link = anotherArticle.getTitle();
            links.put(language, link);
        }
    }
}

