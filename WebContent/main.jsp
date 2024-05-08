<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.modelo.entidades.Usuario" %>
<%@ page import="java.util.logging.Logger" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Home</title>
	
	<!-- ESTILOS DE LA PAGINA -->
	<link rel="stylesheet" href="/CRUD_Productos/css/main.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/header.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/form_login.css" />
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<!-- JQUERY -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!-- JS -->
	<script type="text/javascript" src="/CRUD_Productos/js/main.js"></script>
</head>
<body>
	<% 
		Logger logger = Logger.getLogger("main.jsp");
		Usuario usuario = new Usuario();
		
		// Para mantener los datos del usuario en sesion
		if(request.getSession().getAttribute("s_usuario") != null) {
			usuario = (Usuario)request.getSession().getAttribute("s_usuario");
		}
		
		if(request.getSession().getAttribute("error_message") != null) {
			String mensaje = (String)request.getSession().getAttribute("error_message");
			logger.info(mensaje);
			request.getSession().removeAttribute("error_message");
	%>
		<script>
			mostrarMensaje("Error", "<%= mensaje %>");
		</script>	
	<% } %>
	
	<%
		if(request.getSession().getAttribute("success_message") != null) {
			String mensaje = (String)request.getSession().getAttribute("success_message");
			request.getSession().removeAttribute("success_message");
	%>
		<script>
			mostrarMensaje("Éxito", "<%= mensaje %>");
		</script>
	<% } %>
	
	<!-- Agregando el encabezado de la pagina -->
	<%@ include file="header.jsp" %>
	
	<div id="main-content">
		<% if(usuario.getIdUsuario() == 0) { %>
			<div id="contenedor-login">
	            <form action="/CRUD_Productos/UsuarioControlador" method="post" id="formulario-login">
	                <table id="tabla-login">
	                    <tr class="texto-titulos">
	                        <td>
	                            <p class="titulo-login">Acceso</p>
	                            <p class="subtitulo-login">Escriba sus credenciales de usuario.</p>
	                        </td>
	                    </tr>
	
	                    <tr class="label-izquierda">
	                        <td>
	                            <label for="usuario">Usuario:</label>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td>
	                            <input type="text" id="usuario" name="usuario" maxlength="20">
	                            <p class="error error_usuario"></p>
	                        </td>
	                    </tr>
	                    
	                    <tr class="label-izquierda">
	                        <td>
	                            <label for="contrasenia">Contraseña:</label>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td>
	                            <input type="password" id="contrasenia" name="contrasenia" maxlength="20">
	                            <p class="error error_contrasenia"></p>
	                        </td>
	                    </tr>
	                </table>
	
	                <input type="hidden" id="accion" name="accion" value="acceder" />
	            </form>
	
	            <div class="botones-2opc">
	                <button type="button" id="acceder-login">Acceder</button>
	                <button type="button" id="registrarse-login">Registrarse</button>
	            </div>
	        </div>
		<% } else { %>
			<% response.sendRedirect("/CRUD_Productos/ProductoControlador?accion=consultarProductos&idUsuario=" + usuario.getIdUsuario()); %>
		<% } %>
	</div>
	
	<div class="loader-container" id="loaderContainer">
	  <div class="loader"></div>
	</div>
</body>
</html>