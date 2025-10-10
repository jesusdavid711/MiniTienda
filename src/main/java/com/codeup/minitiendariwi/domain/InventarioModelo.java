/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.minitiendariwi.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import com.codeup.minitiendariwi.exception.ProductoYaExisteException;
import com.codeup.minitiendariwi.exception.StockInsuficienteException;
/**
 *
 * @author Coder
 */

/**
 * Clase que actúa como el Modelo de Datos (M) en la arquitectura MVC.
 * Contiene las estructuras para almacenar el inventario de la tienda.
 */
public class InventarioModelo {
    
     // 1. Crear ArrayList<String> para nombres de productos.
    private final ArrayList<String> nombresProductos;

    // 2. Crear double[] para precios, sincronizado con nombres.
    // Usamos un array primitivo ya que los precios no cambiarán de tamaño frecuentemente
    // y para cumplir estrictamente con el requisito. Se inicializa vacío.
    private double[] precios;

    // 3. Crear HashMap<String, Integer> para stock.
    // La clave será el nombre del producto (String) y el valor será la cantidad (Integer).
    private final HashMap<String, Integer> stockProductos;

    // Variable para rastrear el total de ventas acumuladas en la sesión.
    private double totalVendido;

    /**
     * Constructor para inicializar las estructuras de datos.
     */
    public InventarioModelo() {
        this.nombresProductos = new ArrayList<>();
        this.precios = new double[0]; // Inicialmente vacío
        this.stockProductos = new HashMap<>();
        this.totalVendido = 0.0;
    }

    // -----------------------------------------------------
    // Métodos Utilitarios Requeridos
    // -----------------------------------------------------

    /**
     * Agrega un nuevo producto a todas las estructuras de datos.
     * @param nombre El nombre del producto.
     * @param precio El precio unitario del producto.
     * @param stock La cantidad inicial en stock.
     */
    public void addProducto(String nombre, double precio, int stock) throws ProductoYaExisteException {
    
    // Ahora el Modelo es responsable de la validación de existencia
    if (existeProducto(nombre)) {
        throw new ProductoYaExisteException(nombre);
    }
    
    // Lógica para añadir el producto (original)
    nombresProductos.add(nombre);
    expandPrecios(precio); // Método que expande el array dinámicamente
    stockProductos.put(nombre, stock);
}
    
    /**
     * Expande el array de precios y añade un nuevo precio al final.
     * NOTA: Este método hace que el array 'precios' esté sincronizado con 'nombresProductos'.
     * @param nuevoPrecio El precio a añadir.
     */
    private void expandPrecios(double nuevoPrecio) {
        // Crea un nuevo array con un tamaño mayor (tamaño actual + 1).
        double[] nuevoPrecios = Arrays.copyOf(this.precios, this.precios.length + 1);
        
        // Asigna el nuevo precio a la última posición del nuevo array.
        nuevoPrecios[this.precios.length] = nuevoPrecio; 
        
        // Reemplaza el array de precios actual con el nuevo array.
        this.precios = nuevoPrecios;
    }

    /**
     * Busca el índice de un producto por su nombre en el ArrayList.
     * @param nombre El nombre del producto a buscar.
     * @return El índice del producto, o -1 si no se encuentra.
     */
    public int indexOfNombre(String nombre) {
        return nombresProductos.indexOf(nombre);
    }
    
    // -----------------------------------------------------
    // Métodos Getters y Setters Esenciales
    // -----------------------------------------------------

    public ArrayList<String> getNombresProductos() {
        return nombresProductos;
    }

    public double[] getPrecios() {
        return precios;
    }

    public HashMap<String, Integer> getStockProductos() {
        return stockProductos;
    }
    
    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }
    
    // --- Métodos de Gestión Específicos (para el controlador) ---

    /**
     * Obtiene el precio de un producto dado su nombre.
     * @param nombre El nombre del producto.
     * @return El precio del producto.
     * @throws IllegalArgumentException Si el producto no existe (esto no debería ocurrir si se usa indexOfNombre).
     */
    public double getPrecio(String nombre) {
        int index = indexOfNombre(nombre);
        if (index != -1) {
            // El precio está en el mismo índice del array precios
            return precios[index]; 
        }
        throw new IllegalArgumentException("Producto no encontrado en la lista de precios.");
    }
    
    /**
     * Actualiza el stock de un producto después de una compra.
     * @param nombre El nombre del producto.
     * @param cantidadVendida La cantidad a restar del stock.
     * @throws StockInsuficienteException Si el stock no cubre la cantidad solicitada.
     */
    public void actualizarStock(String nombre, int cantidadComprada) throws StockInsuficienteException {
    // Nota: El Controlador debe garantizar que 'nombre' existe antes de llamar a este método
    
    int stockActual = stockProductos.getOrDefault(nombre, 0);

    // Ahora el Modelo es responsable de la validación de stock
    if (cantidadComprada > stockActual) {
        throw new StockInsuficienteException(nombre, stockActual, cantidadComprada);
    }

    // Actualización de stock (original)
    stockProductos.put(nombre, stockActual - cantidadComprada);
}
    
    /**
     * Verifica si un producto ya existe en el inventario.
     * @param nombre El nombre a verificar.
     * @return true si existe, false en caso contrario.
     */
    public boolean existeProducto(String nombre) {
        return nombresProductos.contains(nombre);
    }
}
