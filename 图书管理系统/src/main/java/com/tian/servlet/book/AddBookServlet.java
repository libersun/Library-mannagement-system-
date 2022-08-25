package com.tian.servlet.book;

import com.tian.service.book.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: addBookServlet
 * Description:
 *
 * @author Tianjiao
 * @date 2021/5/29 11:26
 */
public class AddBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端传递过来的书籍的信息
        String bookId = req.getParameter("bookId");
        String bookName = req.getParameter("bookName");
        String author = req.getParameter("author");
        String inventory = req.getParameter("inventory");
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            bookService.addBook(bookId, bookName, author, Integer.parseInt(inventory));
            //页面跳转,跳到图书管理页面（index.jsp）
            resp.sendRedirect("/books");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
