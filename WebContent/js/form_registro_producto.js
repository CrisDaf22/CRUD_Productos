$(document).ready(function () {
	$("#registrar-producto").on("click", function () {
        var val_form = validar_form_producto();
        
        if(val_form == true) {
        	$("#loaderContainer").css("display", "flex");
        	enviar_form_producto();
        } else {
        	$("#dialog").remove();
        	mostrarMensaje("Advertencia", "Por favor, rellene todos los campos.");
        }
    })

	/* MOSTRAR POP UP DE IMAGEN */
    $("#mostrar-img").on("click", function () {
        console.log("Si entro");
        $("#popup-img").fadeIn();
    })

    $("#cerrar-popup").on("click", function () {
        $("#popup-img").fadeOut();
    })
    
   	/* REGRESAR A TABLERO DE PRODUCTOS */
   	$("#regresar-tablero").on("click", function () {
   		const idUsuario = $("#id_usuario").val();
   		window.location.href = "/CRUD_Productos/ProductoControlador?accion=consultarProductos&idUsuario=" + idUsuario;
   	})
});

function validar_form_producto() {
    var data = new FormData($("#formulario-producto")[0]);
    
    const accion = data.get("accion");

    if(accion == "registrarProducto") {
        const nombre_p = $("#nombre_p").val();
        const precio_p = $("#precio_p").val();
        const cantidad_p = $("#cantidad_p").val();
        const descripcion_p = $("#descripcion_p").val();
        const imagen_p = $("#imagen_p")[0];
        
        const hash = new Map();
        
        if(nombre_p == null || nombre_p == "") {
            hash.set('.error_nombre_p', 'Ingrese el nombre, por favor.');
        }
        if(precio_p == null || precio_p <= 1) {
            hash.set('.error_precio_p', 'Ingrese el precio, por favor.');
        }
        if(cantidad_p == null || cantidad_p <= 0) {
            hash.set('.error_cantidad_p', 'Ingrese la cantidad, por favor.');
        }
        if(descripcion_p == null || descripcion_p == "") {
            hash.set('.error_descripcion_p', 'Ingrese la descripción, por favor.');
        }
        if(imagen_p == null || imagen_p.files.length == 0) {
            hash.set('.error_imagen_p', 'Seleccione una imagen, por favor.');
        }

        if(hash.size > 0) {
            for(let [key, value] of hash) {
                $(key).text(value).show();
            }
            
            return false;
        }
        
        return true;

    } else if (accion == "actualizarProducto"){
        const id_p = $("#id_p").val();
        const nombre_p = $("#nombre_p").val();
        const precio_p = $("#precio_p").val();
        const cantidad_p = $("#cantidad_p").val();
        const descripcion_p = $("#descripcion_p").val();
        
        const hash = new Map();

        if(id_p == null || id_p == "") {
            hash.set('.error_id_p', 'Debe haber un id asignado.');
        }
        if(nombre_p == null || nombre_p == "") {
            hash.set('.error_nombre_p', 'Ingrese el nombre, por favor.');
        }
        if(precio_p == null || precio_p <= 1) {
            hash.set('.error_precio_p', 'Ingrese el precio, por favor.');
        }
        if(cantidad_p == null || cantidad_p <= 0) {
            hash.set('.error_cantidad_p', 'Ingrese la cantidad, por favor.');
        }
        if(descripcion_p == null || descripcion_p == "") {
            hash.set('.error_descripcion_p', 'Ingrese la descripción, por favor.');
        }

        if(hash.size > 0) {
            for(let [key, value] of hash) {
                $(key).text(value).show();
            }
            
            return false;
        }
        
        return true;
    }
}

function enviar_form_producto() {
	$("#formulario-producto").submit();
}