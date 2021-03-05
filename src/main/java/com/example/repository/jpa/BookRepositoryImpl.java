package com.example.repository.jpa;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BookRepositoryImpl extends BaseRepositoryImpl<Book, Long> implements BookRepository {
    public BookRepositoryImpl(EntityManager em) {
        super(Book.class, em);
    }
}
