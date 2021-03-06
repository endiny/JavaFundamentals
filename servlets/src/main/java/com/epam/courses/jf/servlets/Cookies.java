package com.epam.courses.jf.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

@WebServlet("/Cookies")
public class Cookies extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional.ofNullable(request.getParameter("key"))
                .flatMap(key -> Optional.ofNullable(request.getParameter("value"))
                        .map(value -> new Cookie(key, value)))
        .ifPresent(response::addCookie);

        ((PrintWriter) request.getAttribute("writer")).println(
                ofNullable(request.getCookies())
                        .map(cookies -> stream(cookies)
                                .map(cookie -> cookie.getName() + " = " + cookie.getValue())
                                .collect(Collectors.joining("<br/>")))
                        .orElse("No cookies"));

        request.getRequestDispatcher("/cookies.html").include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
