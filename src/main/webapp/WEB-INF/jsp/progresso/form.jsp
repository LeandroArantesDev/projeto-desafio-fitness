<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar progresso - MVC Aula</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/progresso-form.css">
</head>
<body>
<header class="topbar">
    <div class="container">
        <strong>MVC Aula</strong>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/desafios">Meus Desafios</a>
            <c:if test="${usuarioLogado.tipo == 'admin'}">
                <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
    </div>
</header>

<main class="container">
    <div class="page-header">
        <h1>Registrar progresso — ${desafio.nome}</h1>
    </div>

    <div class="card">
        <p>
            Total registrado: <strong>${totalRegistrado}</strong> de <strong>${desafio.metaTotal}</strong> ${desafio.unidadeMedida}
            (<fmt:formatNumber value="${totalRegistrado / desafio.metaTotal * 100}" maxFractionDigits="1"/>%)
        </p>
    </div>

    <div class="card">
        <c:if test="${not empty erro}">
            <div class="alert alert-erro">${erro}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/progresso">
            <input type="hidden" name="participacaoId" value="${participacao.id}">

            <div class="form-group">
                <label for="valorRegistrado">Valor registrado</label>
                <input type="number" id="valorRegistrado" name="valorRegistrado" step="0.01" min="0.01" required>
            </div>

            <div class="form-group">
                <label for="dataRegistro">Data do registro</label>
                <input type="date" id="dataRegistro" name="dataRegistro" required>
            </div>

            <div class="form-group">
                <label for="observacao">Observacao</label>
                <textarea id="observacao" name="observacao"></textarea>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/desafios">Cancelar</a>
            </div>
        </form>
    </div>

    <h2>Historico de check-ins</h2>
    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty historico}">
                <p class="empty">Nenhum check-in registrado ainda.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Data</th>
                        <th>Valor</th>
                        <th>Observacao</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${historico}">
                        <tr>
                            <td>${item.dataRegistro}</td>
                            <td>${item.valorRegistrado} ${desafio.unidadeMedida}</td>
                            <td>${item.observacao}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>
