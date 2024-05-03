$(document).ready(function () {
	$("#registro_usuario").on("click", function () {
        var val_form = validar_form_registro();
        
        /* Si es true enviar ajax a servidor */
        if(val_form == true) {
        	console.log("SI ENVIA EL FORM");
        	enviar_form_usuario();
        }
    })
    
    $("#regresar_login").on("click", function() {
    	window.location.href = "/CRUD_Productos/main.jsp";
    })
});

function validar_form_registro() {
    $(".error").hide();

    const nombre_u = $("#nombre").val();
    const paterno_u = $("#ap_paterno").val();
    const materno_u = $("#ap_materno").val();
    const usuario_u = $("#usuario_r").val();
    const contrasenia_u = $("#contrasenia_r").val();

    const hash = new Map();

    if(nombre_u == null || nombre_u == "") {
        hash.set('.error_nombre', 'Ingrese el nombre, por favor.');
    }
    if(paterno_u == null || paterno_u == "") {
        hash.set('.error_ap_paterno', 'Ingrese el apellido, por favor.');
    }
    if(materno_u == null || materno_u == "") {
        hash.set('.error_ap_materno', 'Ingrese el apellido, por favor.');
    }
    if(usuario_u == null || usuario_u == "") {
        hash.set('.error_usuario_r', 'Ingrese el usuario, por favor.');
    }
    if(contrasenia_u == null || contrasenia_u == "") {
        hash.set('.error_contrasenia_r', 'Ingrese la contraseña, por favor.');
    }

    if(hash.size > 0) {
        for(let [key, value] of hash) {
            $(key).text(value).show();
        }

        return false;
    }

    return true;
}

function enviar_form_usuario() {
	$("#contenedor-tabla").submit();
}