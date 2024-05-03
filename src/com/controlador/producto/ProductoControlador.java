package com.controlador.producto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.modelo.dao.ProductoDAO;
import com.modelo.entidades.Producto;
import com.modelo.entidades.Usuario;

/**
 * Servlet implementation class ProductoControlador
 */
@WebServlet("/ProductoControlador")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB Si supera el tamanio se guarda en disco 
				 maxFileSize=1024*1024*5,      		// 5 MB  Maximo tamanio del archivo individual
				 maxRequestSize=1024*1024*10)		// 10 MB Maximo tamanio de la solicitud
public class ProductoControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(ProductoControlador.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoControlador() {
        super();
        // TODO Auto-generated constructor stub
    }

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
			case "consultarProductos": {
				consultarProductos(request, response);
				
				// Probablement un requestDispatcher
				String jsp = "/iu/tabla_registros.jsp";
				RequestDispatcher rd = request.getRequestDispatcher(jsp);
				rd.forward(request, response);
				//response.sendRedirect("/CRUD_Productos/iu/tabla_registros.jsp");
				break;
			}
			
			// Probablemente sea mejor cargar aqui consultarFormulario
			case "consultarFormularioProducto": {
				consultarFormularioProducto(request, response);
				
				logger.info("SI VAMOS A JSP");
				response.sendRedirect("/CRUD_Productos/iu/form_registro_producto.jsp");
				break;
			}
			
			case "registrarProducto": {		
				logger.info("NO SE POR QUE PUTA ENTRO AQUI");
				registrarProducto(request, response);
				int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
				response.sendRedirect("/CRUD_Productos/ProductoControlador?accion=consultarProductos&idUsuario=" + idUsuario);
				break;
			}
			
			case "actualizarProducto": {
				actualizarProducto(request, response);
				int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
				response.sendRedirect("/CRUD_Productos/ProductoControlador?accion=consultarProductos&idUsuario=" + idUsuario);
				break;
			}
			
			case "eliminarProducto": {
				eliminarProducto(request, response);
				int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
				response.sendRedirect("/CRUD_Productos/ProductoControlador?accion=consultarProductos&idUsuario=" + idUsuario);
				break;
			}
	
			default: {
				break;
			}			
		}
	}
	
	public void consultarProductos(HttpServletRequest request, HttpServletResponse response) {
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		
		List<Producto> productos = null;
		
		if(idUsuario > 0) {
			ProductoDAO dao = new ProductoDAO();
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(idUsuario);
			
			try {
				productos = dao.leerProductos(usuario);
			} catch (SQLException e) {
				// TODO: handle exception
				request.getSession().removeAttribute("error_message");
				request.getSession().setAttribute("error_message", "Ocurrió un error al intentar obtener los registros.");
			}
		}
		
		request.getSession().removeAttribute("listaProductos");
		request.getSession().setAttribute("listaProductos", productos);
	}
	
	public void consultarFormularioProducto(HttpServletRequest request, HttpServletResponse response) {
		String asignarAccion = request.getParameter("asignarAccion");
		
		if(asignarAccion.equals("registrarProducto")) {
			request.getSession().removeAttribute("asignarAccion");
			request.getSession().setAttribute("asignarAccion", "registrarProducto");
			
			logger.info("SI ENTRAMOS A REGISTRAR");
		} else if(asignarAccion.equals("actualizarProducto")) {
			int idProducto = Integer.parseInt(request.getParameter("idProducto"));
			
			ProductoDAO dao = new ProductoDAO();
			Producto pBuscado = new Producto();
			pBuscado.setId_producto(idProducto);
			
			Producto pEncontrado = null;
			
			try {
				pEncontrado = dao.leerProducto(pBuscado);
			} catch (SQLException e) {
				// TODO: handle exception
				request.getSession().removeAttribute("error_message");
				request.getSession().setAttribute("error_message", "Ocurrió un error al intentar obtener el registro.");
			}
			
			request.getSession().removeAttribute("s_producto");
			request.getSession().setAttribute("s_producto", pEncontrado);
			request.getSession().removeAttribute("asignarAccion");
			request.getSession().setAttribute("asignarAccion", "actualizarProducto");
		}
	}
	
	public void registrarProducto(HttpServletRequest request, HttpServletResponse response) {
		String nombreP = request.getParameter("nombre_p");
		float precioP = Float.parseFloat(request.getParameter("precio_p"));
		int cantidadP = Integer.parseInt(request.getParameter("cantidad_p"));
		String descripcionP = request.getParameter("descripcion_p");
		int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
		
		byte[] datosImg = null;
		try {
			Part parteArchivo = request.getPart("imagen_p");
			
			InputStream contenidoArchivo = parteArchivo.getInputStream();
			datosImg = IOUtils.toByteArray(contenidoArchivo);
		} catch (ServletException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(datosImg != null) {
			logger.info("Si hay datos de iamgen");
		} else {
			logger.info("NO hay datos de iamgen");
		}
		
		ProductoDAO dao = new ProductoDAO();
		Producto producto = new Producto();
		producto.setNombre(nombreP);
		producto.setPrecio(precioP);
		producto.setCantidad(cantidadP);
		producto.setDescripcion(descripcionP);
		producto.setImagen(datosImg);
		producto.setUsuario(idUsuario);
		
		try {
			dao.insertarProducto(producto);
			
			request.getSession().removeAttribute("success_message");
			request.getSession().setAttribute("success_message", "El producto se registró de manera correcta.");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			request.getSession().removeAttribute("error_message");
			request.getSession().setAttribute("error_message", "Ocurrió un error al tratar de registrar el producto.");
		}
	}
	
	public void actualizarProducto(HttpServletRequest request, HttpServletResponse response) {
		int idP = Integer.parseInt(request.getParameter("id_p"));
		String nombreP = request.getParameter("nombre_p");
		float precioP = Float.parseFloat(request.getParameter("precio_p"));
		int cantidadP = Integer.parseInt(request.getParameter("cantidad_p"));
		String descripcionP = request.getParameter("descripcion_p");
		int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
		
		byte[] datosImg = null;
		try {
			Part parteArchivo = request.getPart("imagen_p");
			
			if(parteArchivo != null && parteArchivo.getSize() > 0) {
				InputStream contenidoArchivo = parteArchivo.getInputStream();
				datosImg = IOUtils.toByteArray(contenidoArchivo);
			}
		} catch (ServletException | IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		precioP = Math.round(precioP * 100.0f) / 100.0f;
		
		ProductoDAO dao = new ProductoDAO();
		Producto producto = new Producto();
		producto.setId_producto(idP);
		producto.setNombre(nombreP);
		producto.setPrecio(precioP);
		producto.setCantidad(cantidadP);
		producto.setDescripcion(descripcionP);
		producto.setImagen(datosImg);
		producto.setUsuario(idUsuario);
		
		try {
			// Si no se eligió ninguna imagen, entonces se queda la anterior
			dao.actualizarProducto(producto);
			
			request.getSession().removeAttribute("success_message");
			request.getSession().setAttribute("success_message", "El producto se actualizó de manera correcta.");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			request.getSession().removeAttribute("error_message");
			request.getSession().setAttribute("error_message", "Ocurrió un error al tratar de actualizar el producto.");
		}
	}
	
	public void eliminarProducto(HttpServletRequest request, HttpServletResponse response) {
		int idProducto = Integer.parseInt(request.getParameter("idProducto"));
		
		if(idProducto > 0) {
			ProductoDAO dao = new ProductoDAO();
			Producto producto = new Producto();
			producto.setId_producto(idProducto);
			
			try {
				dao.eliminarProducto(producto);
				
				request.getSession().removeAttribute("success_message");
				request.getSession().setAttribute("success_message", "Se eliminó el registro de manera correcta.");
			} catch (SQLException e) {
				request.getSession().removeAttribute("error_message");
				request.getSession().setAttribute("error_message", "Ocurrió un error al tratar de eliminar el registro en la BD.");
			}
		} else {
			request.getSession().removeAttribute("error_message");
			request.getSession().setAttribute("error_message", "Los parametros no son suficientes para eliminar el registro.");
		}
	}
}
