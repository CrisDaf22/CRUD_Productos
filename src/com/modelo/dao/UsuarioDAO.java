package com.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.modelo.entidades.Usuario;

public class UsuarioDAO {
	private Connection conexion;
	
	public UsuarioDAO() {
		
	}
	
	private void getConexion() {
		String usuario = "root";
		String clave = "root";
		// Glassfish tiene temas de seguridad, por eso se debe colocar useSSL=false
		String url = "jdbc:mysql://localhost:3306/CRUD_proyecto?useSSL=false";
		String driverBD = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driverBD);
			conexion = DriverManager.getConnection(url, usuario, clave);
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void insertarUsuario(Usuario usuario) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		
		try {
			cs = conexion.prepareCall("{ call insertarUsuario(?, ?, ?, ?, ?) }");
			cs.setString(1, usuario.getNombre());
			cs.setString(2, usuario.getApellidoPaterno());
			cs.setString(3, usuario.getApellidoMaterno());
			cs.setString(4, usuario.getNombreUsuario());
			cs.setString(5, usuario.getContrasenia());
			
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
	
	public Usuario leerUsuario(Usuario usuario) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		ResultSet rs = null;
		Usuario u = null;
		
		cs = conexion.prepareCall(" { call leerUsuario(?) } ");
		cs.setInt(1, usuario.getIdUsuario());
		
		cs.executeQuery();
		
		rs = cs.getResultSet();
		if(rs.next()) {
			u = new Usuario();
			u.setIdUsuario(rs.getInt("id_usuario"));
			u.setNombre(rs.getString("nombre"));
			u.setApellidoPaterno(rs.getString("apellido_paterno"));
			u.setApellidoMaterno(rs.getString("apellido_materno"));
			u.setNombreUsuario(rs.getString("nombre_usuario"));
			u.setContrasenia(rs.getString("contrasenia"));
		}
		
		return u;
	}
	
	public void actualizarUsuario(Usuario usuario) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		
		try {
			cs = conexion.prepareCall(" { call actualizarUsuario(?, ?, ?, ?, ?, ?) } ");
			cs.setString(1, usuario.getNombre());
			cs.setString(2, usuario.getApellidoPaterno());
			cs.setString(3, usuario.getApellidoMaterno());
			cs.setString(4, usuario.getNombreUsuario());
			cs.setString(5, usuario.getContrasenia());
			cs.setInt(6, usuario.getIdUsuario());
			
			cs.execute();
		} finally {
			if (cs != null) {
				cs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
		}
	}
	
	public void eliminarUsuario(Usuario usuario) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		
		try {
			cs = conexion.prepareCall(" { call eliminarUsuario(?) } ");
			cs.setInt(1, usuario.getIdUsuario());
			
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
	
	public List<Usuario> leerUsuarios() throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		List<Usuario> listaUsuarios = null;
		
		try {
			cs = conexion.prepareCall(" { call leerUsuarios() } ");
			cs.executeQuery();
			
			listaUsuarios = procesarUsuarios(cs.getResultSet());
			
			if(!listaUsuarios.isEmpty() && listaUsuarios != null) {
				return listaUsuarios;
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
		}
	}
	
	public List<Usuario> procesarUsuarios(ResultSet rs) throws SQLException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		while(rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(rs.getInt("id_usuario"));
			usuario.setNombre(rs.getString("nombre"));
			usuario.setApellidoPaterno(rs.getString("apellido_paterno"));
			usuario.setApellidoMaterno(rs.getString("apellido_materno"));
			usuario.setNombreUsuario(rs.getString("nombre_usuario"));
			usuario.setContrasenia(rs.getString("contrasenia"));
			
			usuarios.add(usuario);
		}
		
		return usuarios;
	}
	
	public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) throws SQLException {
		getConexion();
		
		CallableStatement cs = null;
		ResultSet rs = null;
		Usuario u = null;
		
		try {
			cs = conexion.prepareCall("{ call leerUsuarioPorCredenciales(?, ?) }");
			cs.setString(1, usuario.getNombreUsuario());
			cs.setString(2, usuario.getContrasenia());
			cs.executeQuery();
			
			rs = cs.getResultSet();
			if(rs.next()) {
				u = new Usuario();
				u.setIdUsuario(rs.getInt("id_usuario"));
				u.setNombre(rs.getString("nombre"));
				u.setApellidoPaterno(rs.getString("apellido_paterno"));
				u.setApellidoMaterno(rs.getString("apellido_materno"));
				u.setNombreUsuario(rs.getString("nombre_usuario"));
				u.setContrasenia(rs.getString("contrasenia"));
			}
		} finally {
			if(rs != null) {
				rs.close();
			}
			
			if(conexion != null) {
				conexion.close();
			}
		}
		
		return u;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Usuario usuario = new Usuario();
		
		usuario.setIdUsuario(1);
		usuario.setNombre("Elizabeth");
		usuario.setApellidoPaterno("Campos");
		usuario.setApellidoMaterno("Martinez");
		usuario.setNombreUsuario("eli23");
		usuario.setContrasenia("4321");
		
		UsuarioDAO dao = new UsuarioDAO();
		try {
			//dao.insertarUsuario(usuario);
			//System.out.println(dao.leerUsuario(usuario));
			//dao.actualizarUsuario(usuario);
			//System.out.println(dao.leerUsuarios());
			//dao.eliminarUsuario(usuario);
			System.out.println(dao.obtenerUsuarioPorCredenciales(usuario));
			
			System.out.println("Se insert� el registro de manera correcta.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("No se insert� el registro de manera correcta.");
		}

	}

}
