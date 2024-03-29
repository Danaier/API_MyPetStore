package org.csu.mypetstore.api.controller.front;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "CorsFilter")
//@Configuration
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request   = (HttpServletRequest) req;
        //使用xhrFields后的Access-Control-Allow-Origin
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        //使用xhrFields前的Access-Controller-Allow-Origin
        //response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,PATCH,DELETE,PUT");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers","Origin,X-Requested-With,Content-Type,Accept");
        chain.doFilter(req,res);
    }

}
