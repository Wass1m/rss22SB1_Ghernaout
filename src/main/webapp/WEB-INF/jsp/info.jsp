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
<h1>RSS22 INFO</h1>
<a href="/rss22/resume/html">Retour List d'articles</a>

<table>
    <thead>
    <th>RESPONSE ID</th>
    <th>Status</th>
    <th>Description</th>
    </thead>
    <tbody>
    <tr>
        <td>${response.id}</td>
        <td>${response.status}</td>
        <td>${response.description}</td>
    </tr>
    </tbody>
</table>
</body>
</html>
