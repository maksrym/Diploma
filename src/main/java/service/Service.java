package service;

import dto.CategoryDTO;
import dto.NewArticleDTO;
import enums.Language;
import exception.ArticleIsAlreadyExist;
import model.Article;

import java.util.List;

public interface Service {
    Article getArticle(String articleTitle);
    void addArticle(NewArticleDTO articleDTO) throws ArticleIsAlreadyExist;
    void amendArticle(String articleTitle, NewArticleDTO article);
    List<CategoryDTO> getCategoryDTOByLanguage(String language);
    List<CategoryDTO> getCategoryDTOByLanguage(Language language);
    void addCategory(CategoryDTO categoryDTO);
}
