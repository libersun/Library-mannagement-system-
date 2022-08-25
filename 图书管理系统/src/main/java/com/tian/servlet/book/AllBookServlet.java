package com.tian.servlet.book;

import com.tian.pojo.Book;
import com.tian.service.book.BookServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName: BookServlet
 * Description:查询所有书籍的请求
 *
 * @author Tianjiao
 * @date 2021/5/29 10:30
 */
//doGet（）：获取请求参数
public class AllBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Book> books = new BookServiceImpl().getAllBooks();
            if (books != null) {
                req.setAttribute("books", books);//设置值传递要取的值（books）
                req.getRequestDispatcher("static/jsp/index.jsp").forward(req, resp);//服务器端跳转到index.jsp页面
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws EnumConstantNotPresentException {
        this.doGet(req, resp);
    }
}
