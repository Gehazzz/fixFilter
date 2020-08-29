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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FixFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("fixed------------------->");
        ///String body = new BufferedReader(new InputStreamReader(servletRequest.getInputStream())).lines().collect(Collectors.joining());
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(myRequestWrapper, servletResponse);
        //filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    class MyRequestWrapper extends HttpServletRequestWrapper{
        private String body;

        public MyRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            this.body = new BufferedReader(new InputStreamReader(request.getInputStream())).lines().collect(Collectors.joining());
        }

        public String getBody() {
            return body;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));

            ServletInputStream servletInputStream = new ServletInputStream() {


                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
            return servletInputStream;
        }
    }
}
