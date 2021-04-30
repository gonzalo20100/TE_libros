<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="com.emergentes.modelo.Libro"%>
<%
    List<Libro> lista = (List<Libro>)request.getAttribute("lista");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Edicion de Datos Libro</h1>
        <form action="MainController" method="POST">
            <table>               
                <c:forEach var="item" items="${lista}">
                     <input type="hidden" name="id" value="${item.id}">
                    <tr>
                        <td>ISBN</td>
                        <td><input type="text" name="isbn" value="${item.isbn}"></td>
                    </tr>
                    <tr>
                        <td>Titulo</td>
                        <td><input type="text" name="titulo" value="${item.titulo}"></td>
                    </tr>
                    <tr>
                        <td>Categoria</td>
                        <td><input type="text" name="categoria" value="${item.categoria}"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Enviar"></td>
                    </tr>
                </c:forEach>
            </table>
        </form>  
    </body>
</html>