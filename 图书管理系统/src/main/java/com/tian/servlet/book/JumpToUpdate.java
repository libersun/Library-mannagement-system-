package com.tian.servlet.book;

import com.tian.dao.book.BookDaoImpl;
import com.tian.pojo.Book;
import com.tian.utils.BaseDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * ClassName: JumpToUpdate
 * Description:
 */
public class JumpToUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookId = req.getParameter("bookId");
        Connection connection = BaseDao.getConnection();
        try {
            Book book = new BookDaoImpl().getBookById(connection, bookId);
            req.setAttribute("book", book);
            req.getRequestDispatcher("static/jsp/updateBook.jsp").forward(req, resp);//跳转到更改书籍信息的页面
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
