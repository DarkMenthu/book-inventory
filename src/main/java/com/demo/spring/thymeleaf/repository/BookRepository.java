package com.demo.spring.thymeleaf.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.spring.thymeleaf.entity.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
  List<Book> findByTitleContainingIgnoreCase(String keyword);

  @Query("UPDATE Book t SET t.published = :published WHERE t.id = :id")
  @Modifying
  public void updatePublishedStatus(Integer id, boolean published);
}
