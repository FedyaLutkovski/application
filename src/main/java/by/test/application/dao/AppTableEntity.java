package by.test.application.dao;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "app_table", schema = "public", catalog = "application")
public class AppTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long value;

    public AppTableEntity(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public AppTableEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
