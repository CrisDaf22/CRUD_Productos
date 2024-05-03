package com.modelo.entidades;

public class Producto {
	private int id_producto;
	private String nombre;
	private float precio;
	private int cantidad;
	private String descripcion;
	private int usuario;
	private byte[] imagen;
	
	public int getId_producto() {
		return id_producto;
	}
	
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public float getPrecio() {
		return precio;
	}
	
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getUsuario() {
		return usuario;
	}
	
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
	public byte[] getImagen() {
		return imagen;
	}
	
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Producto [id_producto=" + id_producto + ", nombre=" + nombre + ", precio=" + precio + ", cantidad="
				+ cantidad + ", descripcion=" + descripcion + ", usuario=" + usuario + ", imagen="
				+ (imagen != null ? "Hay datos":"No hay datos") + "]";
	}
}
