package com.tian.dao.book;

import com.tian.pojo.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * ClassName: BookDao
 * Description: books表的dao层
 *
 * @author Tianjiao
 * @date 2021/5/28 14:26
 */
public interface BookDao {
   //查阅所有书籍（使主页面直接出现所有书籍的信息）
    public List<Book> getAllBook(Connection connection) throws Exception;

    public Book getBookById(Connection connection, String bookId) throws Exception;

    //添加新的书本信息
    public void addBook(Connection connection, String bookId, String bookName, String author, int inventory) throws Exception;

    //删除书本信息
    public void delBook(Connection connection, String bookId) throws Exception;

    //更改书本信息
    public void updateBook(Connection connection, String bookName, String author, int inventory, String bookId) throws Exception;

    //通过书的名字查询书籍信息（模糊查询）
    public List<Book> getBookByName(Connection connection, String bookName) throws Exception;

    //借书
    public Boolean borrowBook(Connection connection, String bookName, String bookId, String author, String stuId) throws Exception;

    public List<Book> getBooksByStudent(Connection connection, String stuId) throws Exception;

    public void returnBook(Connection connection, String stuId, String bookName) throws SQLException, Exception;
}
