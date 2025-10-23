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

    private final ArrayList<Producto> productos;         // Lista de productos
    private double[] precios;                            // Array paralelo de precios
    private final HashMap<String, Integer> stockProductos; // Mapa nombre → stock
    private double totalVendido;

    public InventarioModelo() {
        this.productos = new ArrayList<>();
        this.precios = new double[0];
        this.stockProductos = new HashMap<>();
        this.totalVendido = 0.0;
    }

    // -----------------------------------------------------
    // Métodos principales del modelo
    // -----------------------------------------------------

    /**
     * Agrega un nuevo producto a todas las estructuras de datos.
     */
    public void addProducto(Producto producto, int stock) throws ProductoYaExisteException {
        if (existeProducto(producto.getNombre())) {
            throw new ProductoYaExisteException(producto.getNombre());
        }

        productos.add(producto);
        stockProductos.put(producto.getNombre(), stock);
        expandPrecios(producto.getPrecio());
    }

    /**
     * Amplía el array de precios sincronizadamente.
     */
    private void expandPrecios(double nuevoPrecio) {
        double[] nuevoArray = Arrays.copyOf(precios, precios.length + 1);
        nuevoArray[precios.length] = nuevoPrecio;
        precios = nuevoArray;
    }

    /**
     * Verifica si un producto ya existe en el inventario.
     */
    public boolean existeProducto(String nombre) {
        return productos.stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    /**
     * Obtiene el precio de un producto por su nombre.
     */
    public double getPrecio(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p.getPrecio();
            }
        }
        throw new IllegalArgumentException("Producto no encontrado: " + nombre);
    }

    /**
     * Actualiza el stock tras una compra.
     */
    public void actualizarStock(String nombre, int cantidadComprada) throws StockInsuficienteException {
        int stockActual = stockProductos.getOrDefault(nombre, 0);

        if (cantidadComprada > stockActual) {
            throw new StockInsuficienteException(nombre, stockActual, cantidadComprada);
        }

        stockProductos.put(nombre, stockActual - cantidadComprada);
    }

    /**
     * Busca la posición de un producto por nombre.
     */
    public int indexOfNombre(String nombre) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    // -----------------------------------------------------
    // Getters y Setters
    // -----------------------------------------------------

    public ArrayList<Producto> getProductos() {
        return productos;
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
}
