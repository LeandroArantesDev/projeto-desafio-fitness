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
  </head>
  <body>
    <header class="topbar">
      <div class="container">
        <strong>MVC Aula</strong>
        <nav>
          <c:if test="${usuarioLogado.tipo == 'admin'}">
            <a href="${pageContext.request.contextPath}/admin">Painel Admin</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
          </c:if>
          <a href="${pageContext.request.contextPath}/home">Home</a>
          <a href="${pageContext.request.contextPath}/desafios">Desafios</a>
          <a href="${pageContext.request.contextPath}/perfis">Perfis</a>
          <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </nav>
      </div>
    </header>

    <main class="container">
      <div class="page-header">
        <h1>Painel</h1>
      </div>

      <div class="grid-cards">
        <a class="menu-card" href="${pageContext.request.contextPath}/usuarios">
          <strong>Usuarios</strong>
          <span>Listar, cadastrar, editar e excluir usuarios.</span>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/desafios">
          <strong>Desafios</strong>
          <span>Ver desafios que participo e que eu criei.</span>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/perfis">
          <strong>Perfis</strong>
          <span>Listar, cadastrar, editar e excluir perfis.</span>
        </a>
      </div>

      <h2>Todos os desafios</h2>
      <div class="table-wrap">
        <c:choose>
            <c:when test="${empty desafios}">
                <p class="empty">Nenhum desafio cadastrado.</p>
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
                    <c:forEach var="desafio" items="${desafios}">
                        <tr>
                            <td>${desafio.id}</td>
                            <td>${desafio.nome}</td>
                            <td>${desafio.categoria}</td>
                            <td>${desafio.metaTotal} ${desafio.unidadeMedida}</td>
                            <td>${desafio.dataInicio} a ${desafio.dataFim}</td>
                            <td>${desafio.status}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/desafios?acao=participar&id=${desafio.id}">Participar</a>
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
