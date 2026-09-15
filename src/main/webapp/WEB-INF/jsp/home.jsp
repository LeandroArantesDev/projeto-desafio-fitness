<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Home - MVC Aula</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/estilo.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/home.css"
    />
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
        <h1>Painel</h1>
      </div>

      <div class="grid-cards">
        <c:if test="${usuarioLogado.tipo == 'admin'}">
          <a class="menu-card" href="${pageContext.request.contextPath}/usuarios">
            <strong>Usuarios</strong>
            <span>Listar, cadastrar, editar e excluir usuarios.</span>
          </a>
        </c:if>
        <a class="menu-card" href="${pageContext.request.contextPath}/desafios">
          <strong>Desafios</strong>
          <span>Ver desafios que participo e que eu criei.</span>
        </a>
      </div>

      <h2>Todos os desafios</h2>
      <div class="grid-cards">
        <c:choose>
          <c:when test="${empty desafios}">
            <p class="empty">Nenhum desafio cadastrado.</p>
          </c:when>
          <c:otherwise>
            <c:forEach var="desafio" items="${desafios}">
              <c:if test="${desafio.status == 'ativo'}">
                <article class="menu-card">
                  <strong>${desafio.nome}</strong>
                  <span>${desafio.categoria}</span>
                  <span>${desafio.metaTotal} ${desafio.unidadeMedida}</span>
                  <span>${desafio.dataInicio} a ${desafio.dataFim}</span>
                  <a href="${pageContext.request.contextPath}/desafios?acao=participar&id=${desafio.id}">Participar</a>
                </article>
              </c:if>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </div>
    </main>
  </body>
</html>
