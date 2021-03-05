package com.example.repository.jpa;

import com.example.annotation.PersistenceLayerTest;
import com.example.dto.AuthorStatistic;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.DbResultNotFoundException;
import com.example.repository.AuthorRepository;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@PersistenceLayerTest
class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DataSet("dataset/init.xml")
    void authorNotExist() {
        //GIVEN
        long notExistId = 99L;

        //WHEN
        DbResultNotFoundException exception = assertThrows(DbResultNotFoundException.class,
                () -> authorRepository.findByIdMandatory(notExistId));

        //THEN
        assertThat(exception.getMessage()).isEqualTo("Entity [Author] with id [99] was not found in DB");
    }

    @Test
    @DataSet("dataset/init.xml")
    void findByEmailIgnoreCase() {
        //GIVEN
        String email = "STEPHEN.KING@EMAIL.COM";

        //WHEN
        Optional<Author> queryResult = authorRepository.findByEmailIgnoreCase(email);

        //THEN
        assertThat(queryResult).hasValueSatisfying(
                author -> assertThat(author.getEmail()).isEqualTo("stephen.king@email.com")
        );
    }

    @Test
    @DataSet("dataset/init.xml")
    void findAuthorStatistic() {
        //WHEN
        List<AuthorStatistic> namesWithAuthors = authorRepository.findAuthorStatistic();

        //THEN
        assertThat(namesWithAuthors).hasSize(2);

        AuthorStatistic stephenKing = namesWithAuthors.get(0);
        assertThat(stephenKing).isNotNull();
        assertThat(stephenKing.getAuthorName()).isEqualTo("Stephen Edwin King");
        assertThat(stephenKing.getBookSize()).isEqualTo(2);

        AuthorStatistic joanneRowling = namesWithAuthors.get(1);
        assertThat(joanneRowling).isNotNull();
        assertThat(joanneRowling.getAuthorName()).isEqualTo("Joanne Rowling");
        assertThat(joanneRowling.getBookSize()).isEqualTo(3);
    }

    @Test
    @DataSet("dataset/init.xml")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void findAllWithBooks() {
        //WHEN
        List<Author> authors = authorRepository.findAllWithBooks();

        //THEN
        assertThat(authors).hasSize(2);

        Author stephenKing = authors.get(0);
        assertThat(stephenKing.getBooks()).hasSize(2);
        assertThat(stephenKing.getBooks())
                .extracting(Book::getName)
                .containsOnly("Pet Sematary", "The Shining");

        Author joanneRowling = authors.get(1);
        assertThat(joanneRowling.getBooks()).hasSize(3);
        assertThat(joanneRowling.getBooks())
                .extracting(Book::getName)
                .containsOnly(
                        "Harry Potter and the Philosopher's Stone",
                        "Harry Potter and the Chamber of Secrets",
                        "Harry Potter and the Prisoner of Azkaban");
    }
}