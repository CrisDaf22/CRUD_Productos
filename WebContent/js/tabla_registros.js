$(document).ready(function (){
	
});

function registrarNuevoProducto() {
	$("#loaderContainer").css("display", "flex");
	window.location.href = "/CRUD_Productos/ProductoControlador?accion=consultarFormularioProducto&asignarAccion=registrarProducto";
}

function consultarProducto(idProducto) {
	$("#loaderContainer").css("display", "flex");
	window.location.href = "/CRUD_Productos/ProductoControlador?accion=consultarFormularioProducto&asignarAccion=actualizarProducto&idProducto=" + idProducto;
}

function eliminarProducto(idProducto, id_usuario) {
	if (confirm("¿Estás seguro de eliminar el registro?") == true) {
		$("#loaderContainer").css("display", "flex");
		window.location.href = "/CRUD_Productos/ProductoControlador?accion=eliminarProducto&idProducto=" + idProducto + "&id_usuario=" + id_usuario;
	}
}