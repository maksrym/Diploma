package config;

import enums.Language;
import model.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repository.TagRepository;

import javax.persistence.Entity;
import java.util.ArrayList;

@SpringBootApplication
@ComponentScan({"converter", "enums"})
@EntityScan("model")
@EnableJpaRepositories("repository")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner runner(TagRepository tagRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				Tag one = new Tag("Batya", Language.ENGLISH);
				Tag two = new Tag("Syna", Language.ENGLISH);

				one.addChildTag(two);
				two.setParentTag(one);

				ArrayList<Tag> tags = new ArrayList<>();
				tags.add(one);
				tags.add(two);
				tagRepository.saveAll(tags);

			}
		};
	}
}
