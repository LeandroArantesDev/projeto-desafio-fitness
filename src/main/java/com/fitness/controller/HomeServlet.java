package com.fitness.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.fitness.service.DesafioService;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    private final DesafioService desafioService = new DesafioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            req.setAttribute("desafios", this.desafioService.listar());
            this.forward(req, resp, "/WEB-INF/jsp/home.jsp");
    }
}
