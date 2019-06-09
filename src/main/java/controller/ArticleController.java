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
        return "index";
    }

    @GetMapping("/article/{name}")
    public String onTest(//@PathVariable("name") String articleName,
                         //HttpServletResponse response,
                         Model model) throws UnsupportedEncodingException {
//        articleName = URLDecoder.decode(articleName, "UTF-8");
//        Article article = service.getArticle(articleName);

        String html = Processor.process("# Intro\n" +
                "> Go ahead, play around with the editor! Be sure to check out **bold** and *italic* styling, or even [links](https://google.com). You can type the Markdown syntax, use the toolbar, or use shortcuts like `cmd-b` or `ctrl-b`.\n" +
                "\n" +
                "## Lists\n" +
                "Unordered lists can be started using the toolbar or by typing `* `, `- `, or `+ `. Ordered lists can be started by typing `1. `.\n" +
                "\n" +
                "#### Unordered\n" +
                "* Lists are a piece of cake\n" +
                "* They even auto continue as you type\n" +
                "* A double enter will end them\n" +
                "* Tabs and shift-tabs work too\n" +
                "\n" +
                "#### Ordered\n" +
                "1. Numbered lists...\n" +
                "2. ...work too!\n" +
                "\n" +
                "## What about images?\n" +
                "![Yes](https://i.imgur.com/sZlktY7.png)");
        System.out.println(html);
        return "articlePage";
    }

    @RequestMapping("/article/add")
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

    @PostMapping("/category/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void addCategory(@RequestBody CategoryDTO categoryDTO) {
        service.addCategory(categoryDTO);
    }
}
