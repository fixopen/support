package com.baremind.utils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by gaolianli on 2015/9/25.
 */
public class CNFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
