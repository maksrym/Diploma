package model;

import org.thymeleaf.util.ArrayUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "page")
    private List<Article> articles;

    public Page() {
        this.articles = new ArrayList<>();
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }
}
