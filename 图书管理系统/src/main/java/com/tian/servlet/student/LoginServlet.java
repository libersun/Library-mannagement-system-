package com.tian.servlet.student;

import com.tian.pojo.Student;
import com.tian.service.student.StudentService;
import com.tian.service.student.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//实现了Servlet接口的java程序LoginServlet
/**
 * ClassName: LoginServlet
 * Description:
 */
public class LoginServlet extends HttpServlet {
    public final static String USER_SESSION = "studentSession";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取学生学号和密码
        String stuId = request.getParameter("stuId");
        String password = request.getParameter("password");
        //调用service方法，进行用户匹配
        StudentService studentService = new StudentServiceImpl();
        Student student = studentService.login(stuId, password);
        //登录成功
        if (null != student) {
            //放入session（用来存放页面数据信息）
            request.getSession().setAttribute(USER_SESSION, student);
            //页面跳转,跳到图书管理页面（index.jsp）
            response.sendRedirect("/books");//(/books)重定向到index.jsp,实现了进入管理主页面就能显示所有书籍信息
        } else {//student=null(studentService.login里面进行密码匹配，若不匹配则会置student为null）
            //页面跳转（login.jsp）带出提示信息--转发
            request.setAttribute("error", "用户名或密码不正确");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
