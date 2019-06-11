package repository;

import enums.Language;
import model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.language = :language")
    List<Category> getCategoriesByLanguage(@Param("language")Language language);

    Category findByName(String name);

    List<Category> findAllByParentCategoryAndLanguage(Category category, Language language);
}
