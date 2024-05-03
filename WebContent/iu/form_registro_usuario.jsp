<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.modelo.entidades.Usuario" %>
<%@ page import="java.util.logging.Logger" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Registrar usuario</title>
	
	<!-- ESTILOS DE LA PAGINA -->
	<link rel="stylesheet" href="/CRUD_Productos/css/main.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/header.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/form_registro_usuario.css" />
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<!-- JQUERY -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!-- JS -->
	<script type="text/javascript" src="/CRUD_Productos/js/main.js"></script>
	<script type="text/javascript" src="/CRUD_Productos/js/form_registro_usuario.js"></script>
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
			request.getSession().removeAttribute("error_message");
	%>
		<script>
			mostrarMensaje('Error', '<%=mensaje %>');
		</script>
	<% } %>
	
	<% 
		if(request.getSession().getAttribute("success_message") != null) {
			String mensaje = (String)request.getSession().getAttribute("success_message");
			request.getSession().removeAttribute("success_message");
	%>
		<script>
			mostrarMensaje('Exito', '<%=mensaje %>');
		</script>
	<% } %>

	<%@ include file="../header.jsp" %>
	
	<div id="main-content">
		<% if(usuario.getIdUsuario() == 0) { %>
			<div id="contenedor-form-usuario">
	            <div class="encabezado-contenido">
	                <p class="titulo-contenido">Registro</p>
	                <p class="subtitulo-contenido">Ingrese la información requerida, por favor.</p>
	            </div>
	
	            <div id="formulario-usuario">
	                <form action="/CRUD_Productos/UsuarioControlador" method="post" id="contenedor-tabla">
	                    <table id="tabla-usuario">
	                        <tr class="fila-usuario">
	                            <td class="col-usuario"><label for="nombre">Nombre:</label></td>
	                            <td class="col-usuario">
	                                <input type="text" id="nombre" name="nombre">
	                                <p class="error error_nombre"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-usuario">
	                            <td class="col-usuario"><label for="ap_paterno">Apellido paterno:</label></td>
	                            <td class="col-usuario">
	                                <input type="text" id="ap_paterno" name="ap_paterno">
	                                <p class="error error_ap_paterno"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-usuario">
	                            <td class="col-usuario"><label for="ap_materno">Apellido materno:</label></td>
	                            <td class="col-usuario">
	                                <input type="text" id="ap_materno" name="ap_materno">
	                                <p class="error error_ap_materno"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-usuario">
	                            <td class="col-usuario"><label for="usuario_r">Nombre de usuario:</label></td>
	                            <td class="col-usuario">
	                                <input type="text" id="usuario_r" name="usuario_r">
	                                <p class="error error_usuario_r"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-usuario">
	                            <td class="col-usuario"><label for="contrasenia_r">Contraseña:</label></td>
	                            <td class="col-usuario">
	                                <input type="password" id="contrasenia_r" name="contrasenia_r">
	                                <p class="error error_contrasenia_r"></p>
	                            </td>
	                        </tr>
	                    </table>  
	                    
	                    <input type="hidden" id="accion" name="accion" value="registrar" />
	                </form>
	
	                <div class="botones-2opc">
	                    <button type="button" id="registro_usuario">Registrar</button>
	                    <button type="button" id="regresar_login">Regresar</button>
	                </div>
	            </div>
	        </div>
		<% } else { %>
			<% response.sendRedirect("/SICOI/ProductoControlador"); %>
		<% } %>
	</div>
</body>
</html>