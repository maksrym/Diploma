package repository;

import enums.Language;
import model.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findAllByLanguageOrderByNameAsc(Language language);
    List<Category> findAllByParentCategoryAndLanguageOrderByNameAsc(Category category, Language language);
    List<Category> findAllByNameContains(String name);
}
