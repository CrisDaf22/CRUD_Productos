$(document).ready(function (){
	// ACCIONES LOGIN
	$("#acceder-login").on("click", function () {
        const usuario = $("#usuario").val();
        const contrasenia = $("#contrasenia").val();

        var formulario = validar_form_login(usuario, contrasenia);

        /* Si es true enviar submit*/
        if(formulario) {
            enviar_form_login();
        } else {
        	mostrarMensaje("Error", "No se pudo enviar el formulario.");
        }
    })

    $("#registrarse-login").on("click", function () {
        window.location.href = "/CRUD_Productos/iu/form_registro_usuario.jsp";
    })
    
    // Mensaje de error
    $("#cerrar-mensaje").on("click", function () {
        $("#popup-mensaje").hide();
    })
});

function validar_form_login(usuario, contrasenia) {
    $(".error").hide();

    const hash = new Map();

    if (usuario == null || usuario == "") {
        hash.set('.error_usuario', 'Ingrese el usuerio, por favor.');
    }

    if(contrasenia == null || contrasenia == "") {
        hash.set('.error_contrasenia', 'Ingrese la contraseña, por favor.');
    }

    if(hash.size > 0) {
        for (let [key, value] of hash) {
            $(key).text(value).show();
        }

        return false;
    }

    return true;
}

function enviar_form_login() {
	$("#formulario-login").submit();
}

function mostrarMensaje(tipoMensaje, mensajeError) {
	const div = $("<div id='dialog' title='" + tipoMensaje + "'></div>");
	const p = $("<p></p>").text(mensajeError);
	
	div.append(p);
	$("body").append(div);
	
	$("#dialog").dialog(); 
}



