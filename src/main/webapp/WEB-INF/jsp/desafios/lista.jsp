<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Desafios - MVC Aula</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
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
        <h1>Desafios</h1>
        <a class="btn" href="${pageContext.request.contextPath}/desafios?acao=novo">Novo desafio</a>
    </div>

    <c:if test="${not empty erro}">
        <div class="alert alert-erro">${erro}</div>
    </c:if>

    <h2>Desafios que participo</h2>
    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty desafiosParticipando}">
                <p class="empty">Voce ainda nao esta participando de nenhum desafio.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Categoria</th>
                        <th>Meta</th>
                        <th>Periodo</th>
                        <th>Status</th>
                        <th>Acoes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="desafio" items="${desafiosParticipando}">
                        <tr>
                            <td>${desafio.id}</td>
                            <td>${desafio.nome}</td>
                            <td>${desafio.categoria}</td>
                            <td>${desafio.metaTotal} ${desafio.unidadeMedida}</td>
                            <td>${desafio.dataInicio} a ${desafio.dataFim}</td>
                            <td>${desafio.status}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/progresso?desafioId=${desafio.id}">Registrar progresso</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <h2>Desafios que eu criei</h2>
    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty desafiosCriados}">
                <p class="empty">Voce ainda nao criou nenhum desafio.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Categoria</th>
                        <th>Meta</th>
                        <th>Periodo</th>
                        <th>Status</th>
                        <th>Acoes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="desafio" items="${desafiosCriados}">
                        <tr>
                            <td>${desafio.id}</td>
                            <td>${desafio.nome}</td>
                            <td>${desafio.categoria}</td>
                            <td>${desafio.metaTotal} ${desafio.unidadeMedida}</td>
                            <td>${desafio.dataInicio} a ${desafio.dataFim}</td>
                            <td>${desafio.status}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/desafios?acao=editar&id=${desafio.id}">Editar</a>
                                <a href="${pageContext.request.contextPath}/desafios?acao=excluir&id=${desafio.id}"
                                   onclick="return confirm('Excluir este desafio?');">Excluir</a>
                            </td>
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
