package service;

import dto.CategoryDTO;
import dto.NewArticleDTO;
import enums.Language;
import exception.ArticleIsAlreadyExist;
import model.Article;
import model.Page;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface Service {
    Article getArticle(String articleTitle);
    void fillArticleModel(String articleTitle, Model model) throws UnsupportedEncodingException;
    void addArticle(NewArticleDTO articleDTO) throws ArticleIsAlreadyExist;
    void amendArticle(String articleTitle, NewArticleDTO article);
    List<Language> getPageRequiredLanguages(Long id);
    List<CategoryDTO> getCategoryDTOByLanguage(String language);
    List<CategoryDTO> getCategoryDTOByLanguage(Language language);
    void addCategory(CategoryDTO categoryDTO);
}
