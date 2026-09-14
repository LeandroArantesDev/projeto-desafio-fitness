package com.fitness.controller;

import com.fitness.service.DesafioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    private static final String VIEW = "/WEB-INF/jsp/home.jsp";

    private final DesafioService desafioService = new DesafioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("desafios", this.desafioService.listar());
        this.forward(req, resp, VIEW);
    }
}
