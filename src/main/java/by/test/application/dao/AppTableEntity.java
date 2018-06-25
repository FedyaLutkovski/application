package by.test.application.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_table", schema = "public", catalog = "application")
public class AppTableEntity {
    @Id
    private String name;
    private Long value;

    public AppTableEntity(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public AppTableEntity() {
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
