package config;

import enums.Language;
import model.Category;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import repository.CategoryRepository;
import service.Service;

import java.util.ArrayList;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner runner(CategoryRepository categoryRepository, Service service) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				Category one = new Category("Batya", Language.ENGLISH);
				Category two = new Category("Syna", Language.ENGLISH);
				one.addChildTag(two);
				two.setParentCategory(one);

				ArrayList<Category> categories = new ArrayList<>();
				categories.add(one);
				categories.add(two);
				categoryRepository.saveAll(categories);
			}
		};
	}
}
