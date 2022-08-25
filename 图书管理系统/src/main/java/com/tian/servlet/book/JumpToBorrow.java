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
 * ClassName: JumpToBorrow
 * Description:
 */
public class JumpToBorrow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端传递过来的书籍的信息
        String stuId = req.getParameter("stuId");
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            List<Book> myBooks = bookService.getBooksByStudent(stuId);
            //页面跳转,跳到借书页面
            req.setAttribute("myBooks", myBooks);
            req.getRequestDispatcher("static/jsp/borrowBooks.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
