<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 30/04/2022
  Time: 01:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>RS22 ITEM BY GUID</h1>
<a href="/rss22/resume/html">Retour List d'articles</a>
<table>
    <thead>
    <th>GUID</th>
    <th>Titre</th>
    <th>Category</th>
    <th>Date</th>
    <th>Image</th>
    <th>Content</th>
    <th>Author Name</th>
    <th>Author Email</th>
    </thead>
    <tbody>
    <tr>
        <td>${myItem.guid}</td>
        <td>${myItem.title}</td>
        <td>${myItem.category.term}</td>
        <td>${myItem.published != null ? myItem.published : myItem.updated}</td>
        <td>${myItem.image.getHref()}</td>
        <td>${myItem.content.value}</td>
        <td>${myItem.author != null ? myItem.author.getName() : myItem.contributor.getName()}</td>
        <td>${myItem.author != null ? myItem.author.getEmail() : myItem.contributor.getEmail()}</td>
    </tr>
    </tbody>
</table>
</body>
</html>
