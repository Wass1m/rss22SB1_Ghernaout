<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 30/04/2022
  Time: 01:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>RS22 INFO DE L'AJOUT MULTIPLE</h1>
<a href="/rss22/resume/html">Retour List d'articles</a>

<h3>Status : ${response.status} </h3>

<h3>Description : ${response.description} </h3>

<h2>Articles Ajoutes</h2>
<table>
  <thead>
  <th>GUID</th>
  </thead>
  <tbody>
  <c:forEach  items="${response.addedGuids}" var ="accArticle">
    <tr>
      <td>${accArticle}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<h2>Articles Refuses (Existant) </h2>
<table>
  <thead>
  <th>GUID</th>
  </thead>
  <tbody>
<c:forEach  items="${response.refusedGuids}" var ="refArticle">
  <tr>
    <td>${refArticle}</td>
  </tr>
</c:forEach>
  </tbody>

</table>
</body>
</html>
