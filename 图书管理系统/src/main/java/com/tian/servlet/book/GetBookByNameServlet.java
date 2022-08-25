package com.tian.servlet.book;

import com.tian.pojo.Book;
import com.tian.service.book.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: GetBookByNameServlet
 * Description:
 */
public class GetBookByNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端传递过来的书籍的信息
        String bookName = req.getParameter("bookName");
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            List<Book> books = bookService.getBookByName(bookName);
            req.setAttribute("books", books);//设置要传递的值
            req.getRequestDispatcher("static/jsp/index.jsp").forward(req, resp);//获得传递的值，并进行页面跳转
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
