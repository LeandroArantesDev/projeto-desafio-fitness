<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <c:choose>
            <c:when test="${empty desafio.id}">Novo desafio</c:when>
            <c:otherwise>Editar desafio</c:otherwise>
        </c:choose>
        - MVC Aula
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/desafios-form.css">
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
        <h1>
            <c:choose>
                <c:when test="${empty desafio.id}">Novo desafio</c:when>
                <c:otherwise>Editar desafio</c:otherwise>
            </c:choose>
        </h1>
    </div>

    <div class="card">
        <c:if test="${not empty erro}">
            <div class="alert alert-erro">${erro}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/desafios">
            <input type="hidden" name="acao" value="salvar">
            <input type="hidden" name="id" value="${desafio.id}">

            <div class="form-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" value="${desafio.nome}" required>
            </div>

            <div class="form-group">
                <label for="descricao">Descricao</label>
                <textarea id="descricao" name="descricao">${desafio.descricao}</textarea>
            </div>

            <div class="form-group">
                <label for="categoria">Categoria</label>
                <select id="categoria" name="categoria" required>
                    <option value="corrida" <c:if test="${desafio.categoria == 'corrida'}">selected</c:if>>Corrida</option>
                    <option value="musculacao" <c:if test="${desafio.categoria == 'musculacao'}">selected</c:if>>Musculacao</option>
                    <option value="ciclismo" <c:if test="${desafio.categoria == 'ciclismo'}">selected</c:if>>Ciclismo</option>
                    <option value="flexoes" <c:if test="${desafio.categoria == 'flexoes'}">selected</c:if>>Flexoes</option>
                    <option value="habito" <c:if test="${desafio.categoria == 'habito'}">selected</c:if>>Habito</option>
                    <option value="outro" <c:if test="${desafio.categoria == 'outro' or empty desafio.categoria}">selected</c:if>>Outro</option>
                </select>
            </div>

            <div class="form-group">
                <label for="tipoMeta">Tipo de meta</label>
                <select id="tipoMeta" name="tipoMeta" required>
                    <option value="repeticoes" <c:if test="${desafio.tipoMeta == 'repeticoes'}">selected</c:if>>Repeticoes</option>
                    <option value="distancia_km" <c:if test="${desafio.tipoMeta == 'distancia_km'}">selected</c:if>>Distancia (km)</option>
                    <option value="tempo_minutos" <c:if test="${desafio.tipoMeta == 'tempo_minutos'}">selected</c:if>>Tempo (minutos)</option>
                    <option value="dias_seguidos" <c:if test="${desafio.tipoMeta == 'dias_seguidos'}">selected</c:if>>Dias seguidos</option>
                </select>
            </div>

            <div class="form-group">
                <label for="metaTotal">Meta total</label>
                <input type="number" id="metaTotal" name="metaTotal" step="0.01" min="0" value="${desafio.metaTotal}" required>
            </div>

            <div class="form-group">
                <label for="dataInicio">Data inicio</label>
                <input type="date" id="dataInicio" name="dataInicio"
                       value="${empty desafio.dataInicio ? '' : fn:substring(desafio.dataInicio, 0, 10)}" required>
            </div>

            <div class="form-group">
                <label for="dataFim">Data fim</label>
                <input type="date" id="dataFim" name="dataFim"
                       value="${empty desafio.dataFim ? '' : fn:substring(desafio.dataFim, 0, 10)}" required>
            </div>

            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <option value="rascunho" <c:if test="${desafio.status == 'rascunho'}">selected</c:if>>Rascunho</option>
                    <option value="ativo" <c:if test="${desafio.status == 'ativo' or empty desafio.status}">selected</c:if>>Ativo</option>
                    <option value="encerrado" <c:if test="${desafio.status == 'encerrado'}">selected</c:if>>Encerrado</option>
                    <option value="cancelado" <c:if test="${desafio.status == 'cancelado'}">selected</c:if>>Cancelado</option>
                </select>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/desafios">Cancelar</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
