package repository;

import enums.Language;
import model.Article;
import model.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Article findFirstByTitle(String title);
    List<Article> findAllByCategoriesAndLanguageOrderByTitle(List<Category> categories, Language language);
    List<Article> findAllByTitleContainsOrAbbreviationContainsOrTextContains(String title, String abbreviation, String text);
}
