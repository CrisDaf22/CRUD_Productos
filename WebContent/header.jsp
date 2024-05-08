<div id="top-content">
    <div id="header">
        <hgroup>
            <h1 id="titulo-banner">SISTEMA DE INVENTARIO</h1>
        </hgroup>
        <p id="subtitulo-banner">Este proyecto permite realizar las operaciones CRUD.</p>
    </div>
</div>

<div id="datos-usuario">
   	<% if(usuario.getNombreUsuario() != null) { %>
	   	<button type="button" id="cerrar-sesion">Cerrar sesi&oacute;n</button>
	   	<p id="nombre-usuario">
	   		Usuario: <%= usuario.getNombreUsuario() %>
	   	</p>
   	<% } else { %>
	   	<p id="nombre-usuario">
	   		No ha iniciado sesión.
	   	</p>
   	<% } %>
</div>