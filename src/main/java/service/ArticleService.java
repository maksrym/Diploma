package service;

import dto.ArticleDTO;
import exception.ArticleIsAlreadyExist;

public interface ArticleService {
    ArticleDTO getArticle(String articleTitle);
    void addArticle(ArticleDTO article) throws ArticleIsAlreadyExist;
    void amendArticle(String articleTitle, ArticleDTO article);
}
