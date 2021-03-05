package com.example.repository;

import com.example.dto.AuthorStatistic;
import com.example.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends BaseRepository<Author, Long> {
    Optional<Author> findByEmailIgnoreCase(String email);

    List<AuthorStatistic> findAuthorStatistic();

    List<Author> findAllWithBooks();
}
