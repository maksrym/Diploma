package service;

import dto.CategoryDTO;
import dto.NewArticleDTO;
import enums.Language;
import exception.ArticleIsAlreadyExist;
import model.Article;
import model.Category;
import model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import repository.ArticleRepository;
import repository.CategoryRepository;
import repository.PageRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Article getArticle(String articleTitle) {
        return null;
    }

    @Override
    @Transactional
    public void addArticle(NewArticleDTO articleDTO) throws ArticleIsAlreadyExist {
        Article article = new Article(articleDTO.getTitle(), articleDTO.getText(), articleDTO.getLanguage());

        Page page;
        if(articleDTO.getPageId() == null) {
            page = new Page();
            page.addArticle(article);
            pageRepository.save(page);
        } else {
            page = pageRepository.findById(articleDTO.getPageId()).get();
        }
        article.setPage(page);

        List<Category> categories = new ArrayList<>();
        for(CategoryDTO categoryDTO : articleDTO.getCategories()) {
            Category category = categoryRepository.findById(categoryDTO.getId()).get();
            categories.add(category);
        }

        if(articleDTO.getAbbreviation() != null) {
            article.setAbbreviation(articleDTO.getAbbreviation());
        }

        article.setCategories(categories);
        article.setUploadTime(articleDTO.getUploadTime());
        article.setLastChange(articleDTO.getUploadTime());

        articleRepository.save(article);
    }

    @Override
    public void amendArticle(String articleTitle, NewArticleDTO article) {

    }

    @Override
    public List<CategoryDTO> getCategoryDTOByLanguage(String language) {
        List<Category> categories = categoryRepository.getCategoriesByLanguage(Language.valueOf(language));

        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        categories.forEach(category -> {
            CategoryDTO categoryDTO = new CategoryDTO(category);
            categoriesDTO.add(categoryDTO);
        });

        return categoriesDTO;
    }

    @Override
    public List<CategoryDTO> getCategoryDTOByLanguage(Language language) {
        return null;
    }

    @Override
    @Transactional
    public void addCategory(CategoryDTO categoryDTO) {
        Category newCategory = new Category(categoryDTO.getName(), categoryDTO.getLanguage());

        if(categoryDTO.getRootCategory().getId() != null) {
            Category rootCategory = categoryRepository.findById(categoryDTO.getRootCategory().getId()).get();
            newCategory.setParentCategory(rootCategory);
        }

        categoryRepository.save(newCategory);
    }
}
