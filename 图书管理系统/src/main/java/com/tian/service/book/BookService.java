package com.tian.service.book;

import com.tian.pojo.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * ClassName: BookService
 * Description:Books的service层
 */
public interface BookService {
    //获得所有的书籍
    public List<Book> getAllBooks() throws Exception;

    //添加书籍信息
    public void addBook(String stuId, String bookName, String author, int inventory) throws Exception;

    //删除书本信息
    public void delBook(String stuId) throws Exception;

    //更改书本信息
    public void updateBook(String stuId, String bookName, String author, int inventory) throws Exception;

    //通过书名获得对应书本的信息（模糊查询）
    public List<Book> getBookByName(String bookName) throws Exception;

    //借书
    public boolean borrowBook(String bookName, String bookId, String author, String stuId) throws Exception;

    public List<Book> getBooksByStudent(String author) throws Exception;

    public void returnBook(String stuId, String bookName) throws SQLException, Exception;
}
