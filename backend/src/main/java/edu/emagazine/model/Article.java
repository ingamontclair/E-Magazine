package edu.emagazine.model;

public class Article {
  private String title;
  private String author;
  private String content;


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Article{" +
      "title='" + title + '\'' +
      ", author='" + author + '\'' +
      ", content='" + content + '\'' +
      '}';
  }
}
