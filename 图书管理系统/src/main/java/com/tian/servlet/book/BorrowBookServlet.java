package com.tian.servlet.book;

import com.tian.service.book.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: borrowBook
 * Description:
 */
public class BorrowBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端传递过来的书籍的信息
        String bookId = req.getParameter("bookId");
        String bookName = req.getParameter("bookName");
        String author = req.getParameter("author");
        String stuId = req.getParameter("stuId");
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            boolean flag = bookService.borrowBook(bookName, bookId, author, stuId);
            if (!flag) {
                req.getRequestDispatcher("/books").forward(req, resp);
            } else {
                req.setAttribute("stuId", stuId);
                //页面跳转,跳转到借书页面
                req.getRequestDispatcher("/jumpToBorrow").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
