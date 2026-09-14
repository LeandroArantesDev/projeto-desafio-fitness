package com.fitness.controller;

import com.fitness.model.Usuario;
import com.fitness.service.DesafioService;
import com.fitness.service.ParticipacaoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controller de Desafio.
 * Ponte entre rota, service e view — sem regra de negocio.
 */
@WebServlet("/desafios")
public class DesafioServlet extends BaseServlet {

    private static final String LISTA = "/WEB-INF/jsp/desafios/lista.jsp";

    private final DesafioService desafioService = new DesafioService();
    private final ParticipacaoService participacaoService = new ParticipacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (this.acao(req)) {
            case "participar" -> this.participar(req, resp);
            default -> this.listar(req, resp);
        }
    }

    private void participar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario usuarioLogado = this.usuarioLogado(req);
        try {
            this.participacaoService.participar(usuarioLogado.getId(), this.paramLong(req, "id"));
            this.redirect(req, resp, "/home");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            this.listar(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario usuarioLogado = this.usuarioLogado(req);
        req.setAttribute("desafiosParticipando", this.desafioService.listarParticipando(usuarioLogado.getId()));
        req.setAttribute("desafiosCriados", this.desafioService.listarCriadosPor(usuarioLogado.getId()));
        this.forward(req, resp, LISTA);
    }

    private Usuario usuarioLogado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (Usuario) session.getAttribute("usuarioLogado");
    }
}
