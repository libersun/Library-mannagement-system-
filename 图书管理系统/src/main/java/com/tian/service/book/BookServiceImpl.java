package com.tian.service.book;

import com.tian.dao.book.BookDaoImpl;
import com.tian.pojo.Book;
import com.tian.utils.BaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * ClassName: BookServiceImpl
 * Description:BookService的实现类
 */
public class BookServiceImpl implements BookService {
   //获得所有的书籍信息
    @Override
    public List<Book> getAllBooks() throws Exception {
        Connection connection = BaseDao.getConnection();//建立数据库连接（加载数据库驱动，连接数据库）
        return new BookDaoImpl().getAllBook(connection);//返回获得所有的书籍信息
    }
    //添加书籍信息
    @Override
    public void addBook(String stuId, String bookName, String author, int inventory) throws Exception {
        Connection connection = BaseDao.getConnection();
        new BookDaoImpl().addBook(connection, stuId, bookName, author, inventory);
    }

    @Override
    public void delBook(String stuId) throws Exception {
        Connection connection = BaseDao.getConnection();
        new BookDaoImpl().delBook(connection, stuId);
    }

    //更改书本信息
    @Override
    public void updateBook(String stuId, String bookName, String author, int inventory) throws Exception {
        Connection connection = BaseDao.getConnection();
        new BookDaoImpl().updateBook(connection, bookName, author, inventory, stuId);
    }
    //通过书名获得指定书本的信息
    @Override
    public List<Book> getBookByName(String bookName) throws Exception {
        Connection connection = BaseDao.getConnection();
        return new BookDaoImpl().getBookByName(connection, bookName);
    }

    //借书
    @Override
    public boolean borrowBook(String bookName, String bookId, String author, String stuId) throws Exception {
        Connection connection = BaseDao.getConnection();
        return new BookDaoImpl().borrowBook(connection, bookName, bookId, author, stuId);
    }

    @Override
    public List<Book> getBooksByStudent(String author) throws Exception {
        Connection connection = BaseDao.getConnection();
        return new BookDaoImpl().getBooksByStudent(connection, author);
    }

    @Override
    public void returnBook(String stuId, String bookName) throws SQLException, Exception {
        Connection connection = BaseDao.getConnection();
        new BookDaoImpl().returnBook(connection, stuId, bookName);
    }
}
