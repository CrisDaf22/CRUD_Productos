<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.Base64" %>    
<%@ page import="java.util.logging.Logger" %>

<%@ page import="com.modelo.entidades.Producto" %>
<%@ page import="com.modelo.entidades.Usuario" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Formulario producto</title>
	
	<!-- CSS -->
	<link rel="stylesheet" href="/CRUD_Productos/css/main.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/header.css" />
	<link rel="stylesheet" href="/CRUD_Productos/css/form_registro_producto.css" />
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<!-- JQUERY -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!-- JS -->
	<script src="/CRUD_Productos/js/main.js"></script>
	<script src="/CRUD_Productos/js/form_registro_producto.js"></script>
</head>
<body>
	<%
		Logger logger = Logger.getLogger("form_registro_producto.jsp");
		
		Usuario usuario = new Usuario();
		Producto producto = null;
		String accion = "";
	
		if(request.getSession().getAttribute("s_usuario") != null) {
			usuario = (Usuario) request.getSession().getAttribute("s_usuario");
		}
		
		if(request.getSession().getAttribute("s_producto") != null) {
			producto = (Producto) request.getSession().getAttribute("s_producto");
			request.getSession().removeAttribute("s_producto");
			logger.info(producto.toString());
		}
		
		if(request.getSession().getAttribute("asignarAccion") != null) {
			accion = (String) request.getSession().getAttribute("asignarAccion");
			request.getSession().removeAttribute("asignarAccion");
		}
	%>

	<%@ include file="../header.jsp" %>

	<div id="main-content">
		<% if(usuario.getIdUsuario() != 0) { %>
			<div id="contenedor-producto">
	            <div class="encabezado-contenido">
	                <p class="titulo-contenido">Producto</p>
	                <p class="subtitulo-contenido">(Registrar/Actualizar)</p>
	            </div>
	
	            <div id="contenedor-form-producto">
	                <form id="formulario-producto" action="/CRUD_Productos/ProductoControlador" method="post" enctype="multipart/form-data">
	                    <table>
	                        <tr class="fila-producto">
	                            <td class="col-producto"><label for="id_p">ID:</label></td>
	                            <td class="col-producto">
	                            	<% if(producto != null && producto.getId_producto() != 0) { %>
	                            		<input type="text" id="id_p" name="id_p" value="<%= producto.getId_producto() %>" readonly/>
	                            	<% } else { %>
	                            		<input type="text" id="id_p" name="id_p" value="0" disabled/>
	                            	<% } %>
	                                <p class="error error_id_p"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-producto">
	                            <td class="col-producto"><label for="nombre_p">Nombre:</label></td>
	                            <td class="col-producto">
	                                <input type="text" id="nombre_p" name="nombre_p" value="<%= producto != null ? producto.getNombre() : "" %>"/>
	                                <p class="error error_nombre_p"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-producto">
	                            <td class="col-producto"><label for="precio_p">Precio:</label></td>
	                            <td class="col-producto">
	                                <input type="number" id="precio_p" name="precio_p" value="<%= producto != null ? producto.getPrecio() : 0.0 %>" />
	                                <p class="error error_precio_p"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-producto">
	                            <td class="col-producto"><label for="cantidad_p">Cantidad:</label></td>
	                            <td class="col-producto">
	                                <input type="number" id="cantidad_p" name="cantidad_p" value="<%= producto != null ? producto.getCantidad() : 0 %>" />
	                                <p class="error error_cantidad_p"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-producto">
	                            <td class="col-producto"><label for="descripcion_p">Descripci&oacute;n:</label></td>
	                            <td class="col-producto">
	                                <input type="text" id="descripcion_p" name="descripcion_p" value="<%= producto != null ? producto.getDescripcion() : "" %>" />
	                                <p class="error error_descripcion_p"></p>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-producto">
	                            <td class="col-producto"><label for="imagen_p">Imagen:</label></td>
	                            <td class="col-producto">
	                                <button type="button" id="mostrar-img">Mostrar imagen</button>
	                            </td>
	                        </tr>
	
	                        <tr class="fila-producto">
	                            <td class="col-producto" colspan="2">
	                                <input type="file" id="imagen_p" name="imagen_p" />
	                                <p class="error error_imagen_p"></p>
	                            </td>
	                        </tr>
	                    </table>
	
	                    <input type="hidden" value="<%= accion %>" id="accion" name="accion" />
	                    <input type="hidden" id="id_usuario" name="id_usuario" value="<%= usuario.getIdUsuario() %>" />
	                </form>
					
					<% logger.info("ESTAMOS EN FORM PRODUCTO"); %>
						
	                <div class="botones-2opc">
	                    <button type="button" id="registrar-producto">Aceptar</button>
	                    <button type="button" id="regresar-tablero">Regresar</button>
	                </div>
	            </div>
	        </div>
	
	        <div id="popup-img">
	        	<% if(producto != null && producto.getImagen().length > 0 ) { 
	        	   	String base64Img = Base64.getEncoder().encodeToString(producto.getImagen());
	        	%>
	        		<img src="data:image/jpeg;base64, <%= base64Img %>" alt="Imagen asociada" />
	        	<% } else { %>
	        		<img src="data:image/jpeg;base64, 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" alt="Imagen asociada" />
	        	<% } %>
	        	
	            <span id="cerrar-popup">X</span>
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