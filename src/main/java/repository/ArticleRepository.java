package repository;

import enums.Language;
import model.Article;
import model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Article findByTitle(String title);
    List<Article> findAllByCategoriesAndLanguage(List<Category> categories, Language language);
}
