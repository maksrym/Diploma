package controller;

import com.github.rjeschke.txtmark.Processor;
import dto.CategoryDTO;
import dto.NewArticleDTO;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private Service service;

    @RequestMapping("/")
    public String onIndex() {
        return "addArticle";
    }

    @GetMapping("/article/{name}")
    public String onTest(//@PathVariable("name") String articleName,
                         //HttpServletResponse response,
                         Model model) throws UnsupportedEncodingException {
//        articleName = URLDecoder.decode(articleName, "UTF-8");
//        Article article = service.getArticle(articleName);

        String html = Processor.process("Article title\n" +
                "Abbreviation\n" +
                "# HEAD\n" +
                "**bold**\n" +
                "*italic*\n" +
                "> цитата\n" +
                "* bullet list\n" +
                "* bullet list 2\n" +
                "\u200B\n" +
                "1. number list\n" +
                "2. number list2\n" +
                "[link](http://google.com)\n" +
                "![some img](https://images.unsplash.com/photo-1483691278019-cb7253bee49f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80)");
        System.out.println(html);
        return "articlePage";
    }

    @GetMapping("/article/add")
    public String addArticlePage() {
        return "addArticle";
    }

    @PostMapping("/article/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void addArticle(@RequestBody NewArticleDTO articleDTO) {
        service.addArticle(articleDTO);
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
