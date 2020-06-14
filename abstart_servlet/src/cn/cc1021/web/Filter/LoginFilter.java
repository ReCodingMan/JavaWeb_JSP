package cn.cc1021.web.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强制转换
        HttpServletRequest request = (HttpServletRequest) req;

        //1、获取资源请求路径
        String uri = request.getRequestURI();
        //2、判断是否包含登录相关资源路径
        if (uri.contains("/login.jsp")
                || uri.contains("/loginServlet")
                || uri.contains("/css/")
                || uri.contains("/js/")
                || uri.contains("/fonts/")
                || uri.contains("/checkCodeServlet")
        ){
            //包含，用户就是想登录。放行
            chain.doFilter(req, resp);
        } else {
            //不包含，需要验证用户是否登录
            //3、从获取 session 中获取 user
            Object user = request.getSession().getAttribute("user");
            if (user != null) {
                //登录了，放行
                chain.doFilter(req, resp);
            } else {
                //没有登录，跳转登录页面
                request.setAttribute("login_msg", "您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(request, resp);
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
