package com.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialBlob;

import com.modelo.entidades.Producto;
import com.modelo.entidades.Usuario;

public class ProductoDAO {
	private Connection conexion;
	
	public void getConexion() {
		String usuario = "root";
		String clave = "root";
		String url = "jdbc:mysql://localhost:3306/crud_proyecto?useSSL=false";
		String driverBD = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driverBD);
			conexion = DriverManager.getConnection(url, usuario, clave);
		} catch (Exception e) {
			Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void insertarProducto(Producto producto) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		Blob imgDatos = null;
		
		try {
			cs = conexion.prepareCall(" { call insertarProducto(?, ?, ?, ?, ?, ?) } ");
			cs.setString(1, producto.getNombre());
			cs.setFloat(2, producto.getPrecio());
			cs.setInt(3, producto.getCantidad());
			cs.setString(4, producto.getDescripcion());
			cs.setInt(5, producto.getUsuario());
			
			if(producto.getImagen() != null) {
				imgDatos = new SerialBlob(producto.getImagen());
				cs.setBlob(6, imgDatos);
			} else {
				cs.setNull(6, java.sql.Types.BLOB);
			}
			
			cs.execute();
		} finally {
			if(cs != null) {
				cs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
			
			if(imgDatos != null) {
				imgDatos.free();
			}
		}
	}
	
	public Producto leerProducto(Producto producto) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		ResultSet rs = null;
		Producto p = new Producto();
		
		try {
			cs = conexion.prepareCall("{ call leerProducto(?) }");
			cs.setInt(1, producto.getId_producto());
			cs.executeQuery();
			
			rs = cs.getResultSet();			
			if(rs.next()) {
				p.setId_producto(rs.getInt("id_producto"));
				p.setNombre(rs.getString("nombre"));
				p.setPrecio(rs.getFloat("precio"));
				p.setCantidad(rs.getInt("cantidad"));
				p.setDescripcion(rs.getString("descripcion"));
				p.setUsuario(rs.getInt("usuario"));
				
				if(rs.getBlob("imagen") != null) {
					Blob imagen = rs.getBlob("imagen");
					
					int tamDatos = (int) imagen.length();
					byte[] datosImg = imagen.getBytes(1, tamDatos);
					
					// Libera los datos de la memoria
					imagen.free();
					
					p.setImagen(datosImg);
				} else {
					p.setImagen(null);
				}
				
				
				/*Blob imagen = rs.getBlob("imagen"); 
				if(imagen != null) {
					int tamDatos = (int) imagen.length();
					byte[] datosImg = imagen.getBytes(1, tamDatos);
					
					// Libera los datos de la memoria
					imagen.free();
					
					p.setImagen(datosImg);
				}*/
			}
			
			cs.execute();
		} finally {
			if(cs != null) {
				cs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
			
			if(rs != null) {
				rs.close();
			}
		}
		
		return p;
	}
	
	public void actualizarProducto(Producto producto) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		Blob imgDatos = null;
		
		try {
			cs = conexion.prepareCall("{ call actualizarProducto(?, ?, ?, ?, ?, ?, ?) }");
			cs.setString(1, producto.getNombre());
			cs.setFloat(2, producto.getPrecio());
			cs.setInt(3, producto.getCantidad());
			cs.setString(4, producto.getDescripcion());
			cs.setInt(5, producto.getUsuario());
			
			if(producto.getImagen() != null) {
				imgDatos = new SerialBlob(producto.getImagen());
				cs.setBlob(6, imgDatos);
			} else {
				cs.setNull(6, java.sql.Types.BLOB);
			}
			
			cs.setInt(7, producto.getId_producto());
			
			cs.execute();
		} finally {
			if(cs != null) {
				cs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
			
			if(imgDatos != null) {
				imgDatos.free();
			}
		}
	}
	
	public List<Producto> leerProductos(Usuario u) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		ResultSet rs = null;
		List<Producto> productos = null;
		
		try {
			cs = conexion.prepareCall("{ call leerProductos(?) }");
			cs.setInt(1, u.getIdUsuario());
			cs.executeQuery();
			
			rs = cs.getResultSet();
			
			productos = obtenerResultados(cs.getResultSet());
			
			if(productos != null && !productos.isEmpty()) {
				return productos;				
			} else {
				return null;
			}
		} finally {
			if(cs != null) {
				cs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
			
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public void eliminarProducto(Producto producto) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		
		try {
			cs = conexion.prepareCall("{ call eliminarProducto(?) }");
			cs.setInt(1, producto.getId_producto());
			cs.execute();
		} finally {
			if(cs != null) {
				cs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
		}
	}
	
	public List<Producto> obtenerResultados(ResultSet rs) throws SQLException {
		List<Producto> listaProductos = new ArrayList<Producto>();
		
		while(rs.next()) {
			Producto p = new Producto();
			p.setId_producto(rs.getInt("id_producto"));
			p.setNombre(rs.getString("nombre"));
			p.setPrecio(rs.getFloat("precio"));
			p.setCantidad(rs.getInt("cantidad"));
			p.setDescripcion(rs.getString("descripcion"));
			p.setUsuario(rs.getInt("usuario"));
			
			if(rs.getBlob("imagen") != null) {
				Blob imagen = rs.getBlob("imagen");
				
				int tamDatos = (int) imagen.length();
				byte[] datosImg = imagen.getBytes(1, tamDatos);
				
				// Libera los datos de la memoria
				imagen.free();
				
				p.setImagen(datosImg);
			} else {
				p.setImagen(null);
			}
			
			/*Blob imagen = rs.getBlob("imagen"); 
			if(imagen != null) {
				int tamDatos = (int) imagen.length();
				byte[] datosImg = imagen.getBytes(1, tamDatos);
				
				// Libera los datos de la memoria
				imagen.free();
				
				p.setImagen(datosImg);
			}*/
			
			listaProductos.add(p);
		}
		
		return listaProductos;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductoDAO dao = new ProductoDAO();
		
		File f = null;
		byte[] contenidoImg = null;
		
		f = new File("D:\\Cristian\\Pictures\\Imagenes para proyectos\\lapiz.jpg");
		System.out.println(f.exists());
		
		try {
			if(f.exists()) {
				contenidoImg = Files.readAllBytes(f.toPath());
			}
			
			Producto p = new Producto();
			p.setId_producto(5);
			p.setNombre("Lapiz A");
			p.setPrecio(5.50F);
			p.setCantidad(10);
			p.setDescripcion("Con imagen.");
			p.setUsuario(2);
			p.setImagen(contenidoImg);
			
			//dao.insertarProducto(p);
			//System.out.println(dao.leerProducto(p).toString());
			//dao.actualizarProducto(p);
			
			Usuario u = new Usuario();
			u.setIdUsuario(2);
			System.out.println(dao.leerProductos(u));
			//dao.eliminarProducto(p);

			System.out.println("Se creó el registro.");
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
