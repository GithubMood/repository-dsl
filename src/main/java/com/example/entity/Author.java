package com.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
public class Author {
    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    private String email;

    @ToString.Include
    private String fullName;

    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(email, author.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
