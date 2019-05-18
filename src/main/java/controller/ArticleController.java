package controller;

import dto.ArticleDTO;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.ArticleRepository;
import service.ArticleService;

public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping
    public void addArticle(@RequestBody ArticleDTO article) {
        articleService.addArticle(article);
    }
}
