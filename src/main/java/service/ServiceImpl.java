package service;

import com.github.rjeschke.txtmark.Processor;
import dto.CategoryDTO;
import dto.NewArticleDTO;
import enums.Language;
import exception.ArticleIsAlreadyExist;
import model.Article;
import model.Category;
import model.Page;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import repository.ArticleRepository;
import repository.CategoryRepository;
import repository.PageRepository;

import javax.management.BadAttributeValueExpException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
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
    public void fillArticleModel(String articleTitle, Model model) throws UnsupportedEncodingException {
        articleTitle = URLDecoder.decode(articleTitle, "UTF-8");
        Article article = articleRepository.findByTitle(articleTitle);

        if (article == null) {
            throw new RuntimeException("Article: " + articleTitle + " is not found.");
        }

        model.addAttribute("title", article.getTitle())
                .addAttribute("abbreviation", article.getAbbreviation())
                .addAttribute("text", markdownToHtml(article.getText()))
                .addAttribute("language", article.getLanguage())
                .addAttribute("categories", article.getCategories())
                .addAttribute("pageId", article.getPage().getId())
                .addAttribute("otherArticles", getOtherArticles(article.getPage()))
                .addAttribute("pageIsFull", pageIsFull(article));

        Hibernate.initialize(article.getCategories());
    }

    private boolean pageIsFull(Article article) {
        Page page = article.getPage();
        int hasArticles = page.getArticles().size();
        int totalNeedLanguages = Language.values().length;

        return hasArticles >= totalNeedLanguages;
    }

    private String markdownToHtml(String markdown) {
        return Processor.process(markdown);
    }

    private HashMap<String, String> getOtherArticles(Page page) throws UnsupportedEncodingException {
        HashMap<String, String> linksList = new HashMap<>();
        List<Article> articles = page.getArticles();

        for (Article article : articles) {
            String urlTitle = URLEncoder.encode(article.getTitle(), "UTF-8");
            linksList.put(article.getLanguage().name(), urlTitle);
        }
        return linksList;
    }

    @Override
    @Transactional
    public void addArticle(NewArticleDTO articleDTO) throws ArticleIsAlreadyExist {
        Article article = new Article(articleDTO.getTitle(), articleDTO.getText(), articleDTO.getLanguage());

        Page page;
        if (articleDTO.getPageId() == null) {
            page = new Page();
            pageRepository.save(page);
        } else {
            page = pageRepository.findById(articleDTO.getPageId()).get();
        }
        article.setPage(page);

        List<Category> categories = new ArrayList<>();
        for (CategoryDTO categoryDTO : articleDTO.getCategories()) {
            Category category = categoryRepository.findById(categoryDTO.getId()).get();
            categories.add(category);
        }

        if (articleDTO.getAbbreviation() != null) {
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
    @Transactional(readOnly = true)
    public List<Language> getPageRequiredLanguages(Long id) {
        List<Language> requiredLanguages = new ArrayList<>(Arrays.asList(Language.values()));

        if (id == null) {
            return requiredLanguages;
        }

        Page page = pageRepository.findById(id).get();
        List<Article> articles = page.getArticles();

        for(Article article : articles) {
            for (Language requiredLanguage : requiredLanguages) {
                if (requiredLanguage.equals(article.getLanguage())) {
                    requiredLanguages.remove(article.getLanguage());
                    break;
                }
            }
        }

        if(requiredLanguages.isEmpty()) {
            throw new RuntimeException("Page already has all translations.");
        }

        return requiredLanguages;
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

        if (categoryDTO.getRootCategory().getId() != null) {
            Category rootCategory = categoryRepository.findById(categoryDTO.getRootCategory().getId()).get();
            newCategory.setParentCategory(rootCategory);
        }

        categoryRepository.save(newCategory);
    }
}
