<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 30/04/2022
  Time: 01:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/rest.css" />">
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>RSS22 SERVER - Mohamed Wassim Ghernaout et Rafik Meghouche</title>
</head>
<body>
<h1>RS22 RESUME DES ARTICLES</h1>

<a href="/insertArticles">Ajouter des articles</a>

<table>
    <thead>
    <th>GUID</th>
    <th>Titre</th>
    <th>Date</th>
    <th>Action</th>
    </thead>
    <tbody>
    <c:forEach  items="${articles}" var ="article">
    <tr>
        <td>${article.guid}</td>
        <td>${article.title}</td>
        <td>${article.published}</td>
        <td class="actions">
            <form:form action="/rss22/delete/ihm/${article.guid}" method="post">
            <input type="submit" value="Supprimer"/>
        </form:form>
        <a href="/rss22/resume/html/${article.guid}">Afficher</a>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
