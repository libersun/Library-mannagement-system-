package com.tian.pojo;

/**
 * ClassName: Book
 * Description: Book的实体类
 */
public class Book {
    /**
     * 图书编号
     */
    private String bookId;
    /**
     * 图书名
     */
    private String bookName;
    /**
     * 作者
     */
    private String author;
    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 学生学号
     */
    private String stuId;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {//重写toString方法
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", inventory=" + inventory +
                '}';
    }
}
