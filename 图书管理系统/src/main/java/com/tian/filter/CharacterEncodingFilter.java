package com.tian.filter;
/*编码过滤器的写入，过滤网站数据，防止出现乱码*/
/*导包*/
import javax.servlet.*;
import java.io.IOException;

/**
 * ClassName: CharacterEncodingFilter
 * Description: 过滤字符编码
 */
public class CharacterEncodingFilter implements Filter {

    /**
     * MethodName: init
     * Description: 初始化：web服务器启动，就以及初始化了，随时等待过滤对象出现！
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /*1.过滤中的所有代码，在过滤特定请求的时候都会执行
    * 2.必须要让过滤器继续同行*/
    //实现filter接口，重写对应的方法
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //        设置请求的编码
        request.setCharacterEncoding("utf-8");
        //        设置响应的编码
        response.setCharacterEncoding("utf-8");
        //        放过本次请求
        chain.doFilter(request, response);//让我们的请求继续走，如果不写，程序到这里就被拦截停止
    }

    /*销毁：web服务器关闭的时候，过滤器会销毁*/
    @Override
    public void destroy() {
    }
}
