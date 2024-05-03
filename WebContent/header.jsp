<div id="top-content">
    <div id="header">
        <hgroup>
            <h1 id="titulo-banner">SISTEMA DE INVENTARIO</h1>
        </hgroup>
        <p id="subtitulo-banner">Este proyecto permite realizar las operaciones CRUD.</p>
    </div>
</div>

<div id="datos-usuario">
    <p id="nombre-usuario">
    	<% if(usuario.getNombreUsuario() != null) { %>
    		Usuario: <%= usuario.getNombreUsuario() %>
    	<% } else { %>
    		No ha iniciado sesión.
    	<% } %>
    </p>
</div>