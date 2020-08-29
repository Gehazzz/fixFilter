package com.example.fixfilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

@Component
//@Order(value = -2147483648)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FixFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("fixed------------------->");
        String body = servletRequest.getReader().lines().collect(Collectors.joining());
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest, body);
        filterChain.doFilter(myRequestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }

    class MyRequestWrapper extends HttpServletRequestWrapper{
        private String body;

        public MyRequestWrapper(HttpServletRequest request, String body) {
            super(request);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new StringReader(this.body));
        }
    }
}
