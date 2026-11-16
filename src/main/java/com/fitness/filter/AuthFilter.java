package com.fitness.filter;

import com.fitness.config.ApiSingleton;
import com.fitness.model.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/home", "/usuarios", "/usuarios/*", "/perfis", "/perfis/*", "/desafios", "/desafios/*", "/progresso", "/progresso/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        Usuario usuarioLogado = (session != null) ? (Usuario) session.getAttribute("usuarioLogado") : null;
        if (usuarioLogado != null && usuarioLogado.getToken() != null) {
            ApiSingleton.setToken(usuarioLogado.getToken());
        }
        try {
            chain.doFilter(request, response);
        } finally {
            ApiSingleton.clearToken();
        }
    }
}
