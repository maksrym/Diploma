package controller;

import dto.ArticleDTO;
import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.ArticleRepository;
import service.ArticleService;

@Controller
public class ArticleController {

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping("/{name}")
    public String onTest(@PathVariable("name")String pageName) {
        return pageName;
    }
}
