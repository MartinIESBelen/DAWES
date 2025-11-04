<%@ page import="org.iesbelen.model.Fabricante" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: sierr
  Date: 31/10/2025
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Crear-productos</title>
</head>
<body>
<h1>Crear nuevo producto</h1>

<form action="${pageContext.request.contextPath}/tienda/productos" method="post">
    <label>Nombre:</label>
    <input type="text" name="nombre" required><br><br>

    <label>Precio:</label>
    <input type="number" name="precio" step="0.01" required><br><br>

    <label>Fabricante:</label>
    <select name="codigo_fabricante" required>
        <%
            // Recuperamos la lista enviada desde el Servlet
            List<Fabricante> fabricantes = (List<Fabricante>) request.getAttribute("listaFabricantes");
            if (fabricantes != null) {
                for (Fabricante f : fabricantes) {
        %>
        <option value="<%= f.getIdFabricante() %>"><%= f.getNombre() %></option>
        <%
                }
            }
        %>
    </select><br><br>

    <button type="submit">Guardar producto</button>
</form>

</body>
</html>
