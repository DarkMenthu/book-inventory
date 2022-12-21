package com.demo.spring.thymeleaf.entity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(length = 180, nullable = false)
  private String title;

  @Column(length = 240)
  private String description;

  @Column(nullable = false)
  private int level;

  @Column
  private boolean published;

  public Book() {

  }

  public Book(String title, String description, int level, boolean published) {
    this.title = title;
    this.description = description;
    this.level = level;
    this.published = published;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", description=" + description + ", level=" + level
        + ", published=" + published + "]";
  }

}
