package com.tian.dao.book;

import com.tian.pojo.Book;
import com.tian.utils.BaseDao;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BookDaoImpl
 * Description:BookDao的实现类
 */
public class BookDaoImpl implements BookDao {
    //查阅所有书籍
    @Test
    @Override
    public List<Book> getAllBook(Connection connection) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        Book book = null;
        if (null != connection) {
            String sql = "select * from `books`";//查询输出数据库所有book表的内容
            Object[] params = {};
            rs = BaseDao.executeQuery(connection, preparedStatement, rs, sql, params);//查阅操作
            int i = 0;
            while (rs.next()) {
                book = new Book();
                book.setInventory(rs.getInt("inventory"));//书的库存
                book.setBookName(rs.getString("bookName"));//书名
                book.setBookId(rs.getString("bookId"));//书的编号
                book.setAuthor(rs.getString("author"));//作者
                books.add(book);
            }
            BaseDao.closeResource(connection, preparedStatement, rs);
        }
        return books;
    }

    @Override
    public Book getBookById(Connection connection, String bookId) throws Exception {
        PreparedStatement preparedStatement = null;
        Book book = null;
        ResultSet rs = null;
        if (null != connection) {
            String sql = "select * from books where bookId=?";
            Object[] params = {bookId};
            rs = BaseDao.executeQuery(connection, preparedStatement, rs, sql, params);
            while (rs.next()) {
                book = new Book();
                book.setInventory(rs.getInt("inventory"));
                book.setBookName(rs.getString("bookName"));
                book.setBookId(rs.getString("bookId"));
                book.setAuthor(rs.getString("author"));
            }
            BaseDao.closeResource(null, preparedStatement, rs);
        }
        return book;
    }

    //新增书籍（添加新的书籍信息）
    @Override
    public void addBook(Connection connection, String bookId, String bookName, String author, int inventory) throws Exception {
        PreparedStatement preparedStatement = null;
        Book book = null;
        if (null != connection) {
            String sql = "INSERT INTO `books`(`bookId`,`bookName`,`author`,`inventory`) VALUES\n" +
                    "(?,?,?,?)";
            Object[] params = {bookId, bookName, author, inventory};
            BaseDao.executeUpdate(connection, preparedStatement, sql, params);//数据库更新操作
            BaseDao.closeResource(connection, preparedStatement, null);
        }
    }

    //删除指定的书籍信息
    @Override
    public void delBook(Connection connection, String bookId) throws Exception {
        PreparedStatement preparedStatement = null;
        if (null != connection) {
            String sql = "DELETE FROM `books` where bookId = ?";
            Object[] params = {bookId};
            BaseDao.executeUpdate(connection, preparedStatement, sql, params);//数据库更新操作
            BaseDao.closeResource(connection, preparedStatement, null);
        }
    }

    //更改书本信息
    @Override
    public void updateBook(Connection connection, String bookName, String author, int inventory, String bookId) throws Exception {
        PreparedStatement preparedStatement = null;
        if (null != connection) {
            String sql = "UPDATE books SET bookName=?,author=?,inventory=? WHERE bookId=?";
            Object[] params = {bookName, author, inventory, bookId};
            BaseDao.executeUpdate(connection, preparedStatement, sql, params);//数据库更新操作
            BaseDao.closeResource(connection, preparedStatement, null);
        }
    }

    //通过书本名字查阅对应的书本信息 的实现类（模糊查询）
    @Override
    public List<Book> getBookByName(Connection connection, String bookName) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Book book = null;
        List<Book> books = new ArrayList<>();
        if (null != connection) {
            String sql = "select * from books where bookName like ?";//SQL条件查询通过Like限制bookName条件进行查询结构的输出
            Object[] params = {"%" + bookName + "%"};//中间夹的bookName即为sql语句中？的内容
            rs = BaseDao.executeQuery(connection, preparedStatement, rs, sql, params);
            while (rs.next()) {//把相关对应的书本信息放到book对象中
                book = new Book();
                book.setInventory(rs.getInt("inventory"));
                book.setBookName(rs.getString("bookName"));
                book.setBookId(rs.getString("bookId"));
                book.setAuthor(rs.getString("author"));
                books.add(book);
            }
            BaseDao.closeResource(connection, preparedStatement, rs);
        }
        return books;
    }

    //借书
    @Override
    public Boolean borrowBook(Connection connection, String bookName, String bookId, String author, String stuId) throws Exception {
        PreparedStatement preparedStatement = null;
        Book book = null;
        if (null != connection) {
            List<Book> books = this.getBooksByStudent(connection, stuId);

            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getBookName().equals(bookName)) {
                    System.out.println("books.get(i).getBookName().equals(bookName): " + books.get(i).getBookName().equals(bookName));
                    return false;
                }
            }

            connection = BaseDao.getConnection();
            String sql01 = "UPDATE `books` SET `inventory`=`inventory`-1 WHERE `bookName` = ?";//让库存减1
            Object[] params01 = {bookName};
            String sql02 = "INSERT INTO `mybooks`(`bookId`,`bookName`,`author`,`stuId`) VALUES(?,?,?,?)";//在借阅表中插入新的借阅信息
            Object[] params02 = {bookId, bookName, author, stuId};
            BaseDao.executeUpdate(connection, preparedStatement, sql01, params01);//数据库更新操作
            BaseDao.executeUpdate(connection, preparedStatement, sql02, params02);
            BaseDao.closeResource(connection, preparedStatement, null);
        }
        return true;
    }

    @Override
    public List<Book> getBooksByStudent(Connection connection, String stuId) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Book book = null;
        List<Book> books = new ArrayList<>();
        if (null != connection) {
            String sql = "select * from mybooks where stuId = ?";
            Object[] params = {stuId};
            rs = BaseDao.executeQuery(connection, preparedStatement, rs, sql, params);
            while (rs.next()) {
                book = new Book();
                book.setBookName(rs.getString("bookName"));
                book.setBookId(rs.getString("bookId"));
                book.setAuthor(rs.getString("author"));
                book.setStuId(rs.getString("stuId"));
                books.add(book);
            }
            BaseDao.closeResource(connection, preparedStatement, rs);
        }
        return books;
    }

    @Override
    public void returnBook(Connection connection, String stuId, String bookName) throws Exception {
        PreparedStatement preparedStatement = null;
        Book book = null;
        if (null != connection) {
            String sql01 = "UPDATE `books` SET `inventory`=`inventory` + 1 WHERE `bookName` = ?";
            Object[] params01 = {bookName};
            String sql02 = "DELETE FROM `mybooks` WHERE `stuId` = ? AND `bookName` = ?";
            Object[] params02 = {stuId, bookName};
            BaseDao.executeUpdate(connection, preparedStatement, sql01, params01);
            BaseDao.executeUpdate(connection, preparedStatement, sql02, params02);
            BaseDao.closeResource(connection, preparedStatement, null);
        }
    }
}