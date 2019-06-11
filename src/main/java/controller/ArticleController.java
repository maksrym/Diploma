package controller;

import dto.CategoryDTO;
import dto.NewArticleDTO;
import enums.Language;
import model.Article;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private Service service;

    @RequestMapping("/")
    public String onIndex() {
        return "forward:/category";
    }

    @GetMapping("/article/{name}")
    public String getArticle(@PathVariable("name") String articleName,
                         HttpServletResponse response,
                         Model model) throws UnsupportedEncodingException {
        articleName = URLDecoder.decode(articleName, "UTF-8");
        service.fillArticleModel(articleName, model);
        return "articlePage";
    }

    @GetMapping("/article/add")
    public String addArticlePage(@RequestParam(name = "page", required = false)Long id,
                                 Model model) {
        List<Language> requiredLanguages = service.getPageRequiredLanguages(id);
        model.addAttribute("requiredLanguages", requiredLanguages);

        return "addArticle";
    }

    @PostMapping("/article/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void addArticle(@RequestBody NewArticleDTO articleDTO) {
        service.addArticle(articleDTO);
    }

    @RequestMapping(value = {"/category", "/category/{name}"})
    public String getCategory(@PathVariable(required = false) String name,
                              @RequestParam(defaultValue = "ENGLISH", required = false)Language language,
                              Model model) throws UnsupportedEncodingException {
        if (name == null) {
            List<Article> articlesWithoutCategory = service.getArticlesWithoutCategory(language);
            model.addAttribute("articles", articlesWithoutCategory)
                    .addAttribute("subcategories", service.getAllRootCategories(language))
                    .addAttribute("language", language);
            return "categoriesPage";
        }

        name = URLEncoder.encode(name, "UTF-8");
        List<Category> categories = service.getCategoryTree(name);
        Category selectedCategory = categories.get(categories.size() - 1);
        List<Article> articles = selectedCategory.getArticles();
        model.addAttribute("categories", categories)
                .addAttribute("articles", articles)
                .addAttribute("subcategories", selectedCategory.getSubcategory())
                .addAttribute("language", selectedCategory.getLanguage());
        return "categoriesPage";
    }

    @RequestMapping("/category/getList")
    @ResponseBody
    public List<CategoryDTO> getCategoriesList(@RequestParam String language) {
        return service.getCategoryDTOByLanguage(language);
    }

    @GetMapping("/category/add")
    public String addCategoryPage() {
        return "addCategory";
    }

    @PostMapping("/category/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void addCategory(@RequestBody CategoryDTO categoryDTO) {
        service.addCategory(categoryDTO);
    }
}
