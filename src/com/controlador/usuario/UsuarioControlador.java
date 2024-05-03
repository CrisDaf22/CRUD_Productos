package com.controlador.usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.modelo.dao.UsuarioDAO;
import com.modelo.entidades.Usuario;

/**
 * Servlet implementation class UsuarioControlador
 */
@WebServlet("/UsuarioControlador")
public class UsuarioControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(UsuarioControlador.class.getName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = request.getParameter("accion");
		
		switch (accion) {
			case "acceder": {				
				verificarCredenciales(request, response);
				
				response.sendRedirect("/CRUD_Productos/main.jsp");
				break;
			}
				
			case "registrar": {
				logger.info("ENTRAMOS A REGISTRAR");
				registrarUsuario(request, response);
				
				response.sendRedirect("/CRUD_Productos/iu/form_registro_usuario.jsp");
				break;
			}
		
			default: {
				break;
			}
		}
	}
	
	/* FUNCIONES DEL SERVLET */
	private void verificarCredenciales(HttpServletRequest request, HttpServletResponse response) {
		String nombre_usuario = request.getParameter("usuario");
		String contrasenia = request.getParameter("contrasenia");
		
		logger.info(nombre_usuario);
		logger.info(contrasenia);
		
		UsuarioDAO dao = new UsuarioDAO();
		
		Usuario usuario = new Usuario();
		usuario.setNombreUsuario(nombre_usuario);
		usuario.setContrasenia(contrasenia);
		
		Usuario usuario_respuesta = null;
		
		try {
			usuario_respuesta = dao.obtenerUsuarioPorCredenciales(usuario);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(usuario_respuesta != null) {
			request.getSession().removeAttribute("s_usuario");
			request.getSession().setAttribute("s_usuario", usuario_respuesta);
		} else {
			request.getSession().removeAttribute("error_message");
			request.getSession().setAttribute("error_message", "No se encontró un usuario con esas credenciales.");
		}
	}
	
	public void registrarUsuario(HttpServletRequest request, HttpServletResponse response) {
		logger.info("ENTRAMOS A registarUsuario");
		String nombre = request.getParameter("nombre");
		String ap_paterno = request.getParameter("ap_paterno");
		String ap_materno = request.getParameter("ap_materno");
		String usuario = request.getParameter("usuario_r");
		String contrasenia = request.getParameter("contrasenia_r");
		
		logger.info("Entro en registrarUsuario");
		
		Usuario nuevo_usuario = new Usuario();
		nuevo_usuario.setNombre(nombre);
		nuevo_usuario.setApellidoPaterno(ap_paterno);
		nuevo_usuario.setApellidoMaterno(ap_materno);
		nuevo_usuario.setNombreUsuario(usuario);
		nuevo_usuario.setContrasenia(contrasenia);
		
		UsuarioDAO dao = new UsuarioDAO();
		
		try {
			dao.insertarUsuario(nuevo_usuario);
			
			request.getSession().removeAttribute("success_message");
			request.getSession().setAttribute("success_message", "El usuario se registró de manera correcta.");
		} catch (SQLException e) {
			// TODO: handle exception
			request.getSession().removeAttribute("error_message");
			request.getSession().setAttribute("error_message", "Ocurrió un error al registrar el usuario.");
			
			e.printStackTrace();
		}
	}
}
