<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.logging.Logger" %>

<%@ page import="com.modelo.entidades.Producto" %>
<%@ page import="com.modelo.entidades.Usuario" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Tabla de registros</title>
	
	<!-- CSS -->
	<link rel="stylesheet" href="/CRUD_Productos/css/main.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/header.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/tabla_registros.css" />
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<!-- JQUERY -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!-- JS -->
	<script type="text/javascript" src="/CRUD_Productos/js/main.js"></script>
	<script type="text/javascript" src="/CRUD_Productos/js/tabla_registros.js"></script>
	
</head>
<body>
	<%
		Logger logger = Logger.getLogger("tabla_registros.jsp");	
	
		Usuario usuario = new Usuario();
		List<Producto> listaProductos = null;
		
		if(request.getSession().getAttribute("s_usuario") != null) {
			usuario = (Usuario) request.getSession().getAttribute("s_usuario");
		}
		
		if(request.getSession().getAttribute("listaProductos") != null) {
			listaProductos = (List<Producto>) request.getSession().getAttribute("listaProductos");
		}
		
		if(request.getSession().getAttribute("error_message") != null) {
			String mensaje = (String) request.getSession().getAttribute("error_message");
			request.getSession().removeAttribute("error_message");
	%>
		<script>
			mostrarMensaje('Error', '<%=mensaje %>');	
		</script>
	<% } %>
	
	<%
		if(request.getSession().getAttribute("success_message") != null) {
			String mensaje = (String) request.getSession().getAttribute("success_message");
			request.getSession().removeAttribute("success_message");
	%>
		<script>
			mostrarMensaje('Éxito', '<%=mensaje %>');	
		</script>
	<% } %>
	
	<%@ include file="../header.jsp" %>

	<div id="main-content">
		<% if(usuario.getIdUsuario() != 0) { %>
			<div id="contenedor-registros">
	            <div class="encabezado-contenido">
	                <p class="titulo-contenido">TABLA DE PRODUCTOS</p>
	            </div>
	
	            <div id="boton-arriba">
	                <button id="abrir-form-registro" onclick="registrarNuevoProducto()">
	                    Agregar registro
	                </button>
	            </div>
	
	            <div id="contenedor-tabla">
	                <table id="tabla-registros">
	                    <tr class="fila-registros">
	                        <th>Nombre</th>
	                        <th>Precio</th>
	                        <th>Cantidad</th>
	                        <th>Acci&oacute;n</th>
	                    </tr>
						
						<% if(listaProductos != null) { %>
							<% for(Producto p: listaProductos) { %>
								<tr class="fila-registros">
			                        <td class="col-registros"><a href="#" onclick="consultarProducto(<%= p.getId_producto() %>);"><%= p.getNombre() %></a></td>
			                        <td class="col-registros"><%= p.getPrecio() %></td>
			                        <td class="col-registros"><%= p.getCantidad() %></td>
			                        <td class="col-registros">
			                            <button type="button" onclick="eliminarProducto(<%= p.getId_producto() %>, <%= usuario.getIdUsuario() %>);">
			                                Eliminar
			                            </button>
			                        </td>
			                    </tr>
							<% } %>
						<% } else { %>
							<tr class="filra-registros">
								<td colspan="4" class="col-registros">No se registrado ning&uacute;n producto.</td>
							</tr>
						<% } %>
	                </table>
	            </div>
	        </div>
		<% } else { %>
			<% response.sendRedirect("/CRUD_Productos/main.jsp"); %>
		<% } %>
	</div>
	
	<div class="loader-container" id="loaderContainer">
	  <div class="loader"></div>
	</div>
</body>
</html>