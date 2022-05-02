<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/style/rest.css" />">
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>RSS22 SERVER - Add Article</title>
</head>
<body>
<a href="/rss22/resume/html">Retour List d'articles</a>

<%--<c:if test="${addBookSuccess}">--%>
<%--    <div>Successfully added Book with ISBN: ${savedBook.isbn}</div>--%>
<%--</c:if>--%>

<c:url var="add_articles_url" value="/rss22/ihm/insert"/>
<%--<c:url var="add_articles_file" value="/rss22/insert/file"/>--%>

<form:form cssClass="forms" action="${add_articles_url}" method="post" modelAttribute="formField">
    <form:label path="flux">Flux XML (Format TEXT): </form:label> <form:textarea rows="10" cols="100" type="text" path="flux"/>
    <input class="sub-button" type="submit" value="submit Flux"/>
</form:form>

<form:form cssClass="forms" action="${add_articles_url}" method="post" enctype="multipart/form-data" modelAttribute="formField">
    <form:label path="file">Flux XML (Format Fichier XML): </form:label> <form:input accept="text/xml" type="file" path="file"/>
    <input class="sub-button" type="submit" value="submit File"/>
</form:form>
</body>
</html>