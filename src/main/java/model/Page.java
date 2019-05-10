package model;

import org.thymeleaf.util.ArrayUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tables")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "page")
    private List<Article> articles;

    protected Page() {
        this.articles = new ArrayList<>();
    }
}
