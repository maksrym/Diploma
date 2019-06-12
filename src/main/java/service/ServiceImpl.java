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
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
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
    @Transactional(readOnly = true)
    public Article getArticle(String articleTitle) {
        Article article = articleRepository.findFirstByTitle(articleTitle);;
        Hibernate.initialize(article.getCategories());
        return article;
    }

    @Override
    @Transactional
    public void fillArticleModel(String articleTitle, Model model) throws UnsupportedEncodingException {
        articleTitle = URLDecoder.decode(articleTitle, "UTF-8");
        Article article = articleRepository.findFirstByTitle(articleTitle);

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
    public void addArticle(Article article) throws ArticleIsAlreadyExist {

        Page page;
        if (article.getPage() == null) {
            page = new Page();
            pageRepository.save(page);
        } else {
            page = pageRepository.findById(article.getPage().getId()).get();
        }
        article.setPage(page);



        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void addArticle(NewArticleDTO articleDTO) throws ArticleIsAlreadyExist {
        Article article = new Article(articleDTO.getTitle(), articleDTO.getText(), articleDTO.getLanguage());

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

        addArticle(article);
    }

    @Override
    @Transactional
    public void amendArticle(String articleTitle, NewArticleDTO articleDTO) {
        Article article = articleRepository.findFirstByTitle(articleTitle);
        article.setText(articleDTO.getText());
        article.setLastChange(new DateTime());

        List<CategoryDTO> categoryDTOs = articleDTO.getCategories();
        List<Category> categories = new ArrayList<>();

        for(CategoryDTO categoryDTO : categoryDTOs) {
            Category category = categoryRepository.findById(categoryDTO.getId()).get();
            categories.add(category);
        }

        article.setCategories(categories);
        articleRepository.save(article);
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

        for (Article article : articles) {
            for (Language requiredLanguage : requiredLanguages) {
                if (requiredLanguage.equals(article.getLanguage())) {
                    requiredLanguages.remove(article.getLanguage());
                    break;
                }
            }
        }

        if (requiredLanguages.isEmpty()) {
            throw new RuntimeException("Page already has all translations.");
        }

        return requiredLanguages;
    }

    @Override
    public List<CategoryDTO> getCategoryDTOByLanguage(String language) {
        List<Category> categories = categoryRepository.findAllByLanguageOrderByNameAsc(Language.valueOf(language));

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

    @Override
    public List<Category> getAllRootCategories(Language language) {
        return categoryRepository.findAllByParentCategoryAndLanguageOrderByNameAsc(null, language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getArticlesWithoutCategory(Language language) {
        return articleRepository.findAllByCategoriesAndLanguageOrderByTitle(null, language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoryTree(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        Hibernate.initialize(category.getArticles());
        Hibernate.initialize(category.getSubcategory());

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);

        Category categoryParent = category.getParentCategory();
        while (categoryParent != null) {
            categories.add(0, categoryParent);
            categoryParent = categoryParent.getParentCategory();
        }
        return categories;
    }

    @Override
    public List<Category> findCategories(String category) {
        if (category.isEmpty()) {
            return new ArrayList<>();
        }
        return categoryRepository.findAllByNameContains(category);
    }

    @Override
    public List<Article> findArticles(String article) {
        if (article.isEmpty()) {
            return new ArrayList<>();
        }
        return articleRepository.findAllByTitleContainsOrAbbreviationContainsOrTextContains(article, article, article);
    }

    @Override
    public void deleteArticle(String articleTitle) {
        Article article = articleRepository.findFirstByTitle(articleTitle);
        articleRepository.delete(article);
    }
}
