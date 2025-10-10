/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.minitiendariwi.service;

import com.codeup.minitiendariwi.domain.InventarioModelo;
import com.codeup.minitiendariwi.ui.TiendaVista;
import com.codeup.minitiendariwi.exception.ProductoYaExisteException;
import com.codeup.minitiendariwi.exception.StockInsuficienteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Coder
 */
/**
 * Clase que actúa como el Controlador/Servicio (C) en la arquitectura MVC.
 * Contiene la lógica de negocio y coordina la interacción entre la Vista y el Modelo.
 */
public class TiendaServicio {
    
    // Instancia del Modelo para acceder y manipular los datos.
    private final InventarioModelo modelo;

    /**
     * Constructor. Inicializa el Modelo.
     */
     public TiendaServicio() {
        this.modelo = new InventarioModelo();
        
        // El bloque try-catch es OBLIGATORIO porque addProducto ahora lanza una excepción
        try {
            // Datos de prueba
            modelo.addProducto("Portatil Riwi", 1200.00, 5);
            modelo.addProducto("Teclado Mecanico", 85.50, 10);
            
        } catch (ProductoYaExisteException e) {
            // En un caso de prueba, si esto sucede, solo lo imprimimos por consola.
            System.err.println("Advertencia al inicializar datos: " + e.getMessage());
        }
    }
    
    // -----------------------------------------------------
    // Lógica para las Opciones del Menú (Tarea 3)
    // -----------------------------------------------------

    /**
     * TAREA 3.1: Agregar producto.
     * Pide nombre, precio y stock. Valida entradas y no duplicados.
     */
    public void agregarProducto() {
    String nombre = TiendaVista.pedirString("Ingrese el nombre del nuevo producto:").trim();
    
    if (nombre.isEmpty()) {
        TiendaVista.mostrarError("El nombre del producto no puede estar vacío.");
        return;
    }
    
    try {
        String precioStr = TiendaVista.pedirString("Ingrese el precio unitario de " + nombre + ":");
        if (precioStr == null || precioStr.isEmpty()) return;
        double precio = Double.parseDouble(precioStr);
        
        String stockStr = TiendaVista.pedirString("Ingrese la cantidad inicial de stock de " + nombre + ":");
        if (stockStr == null || stockStr.isEmpty()) return;
        int stock = Integer.parseInt(stockStr);

        if (precio <= 0 || stock < 0) {
             TiendaVista.mostrarError("El precio debe ser positivo y el stock no puede ser negativo.");
             return;
        }
        
        // El Modelo lanza la excepción si ya existe
        modelo.addProducto(nombre, precio, stock);
        TiendaVista.mostrarMensaje("✅ Producto '" + nombre + "' agregado exitosamente.", "Éxito");

    } catch (NumberFormatException e) {
        TiendaVista.mostrarError("ERROR de formato: El precio y el stock deben ser números válidos.");
    } catch (ProductoYaExisteException e) {
        // Captura la excepción personalizada y muestra su mensaje (definido en la clase Excepción)
        TiendaVista.mostrarError(e.getMessage());
    }
}

    /**
     * TAREA 3.2: Listar inventario.
     * Recorre las estructuras y muestra todos los productos.
     */
    public void listarInventario() {
        ArrayList<String> nombres = modelo.getNombresProductos();
        double[] precios = modelo.getPrecios();
        HashMap<String, Integer> stock = modelo.getStockProductos();

        if (nombres.isEmpty()) {
            TiendaVista.mostrarMensaje("El inventario está vacío.", "Inventario");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- INVENTARIO ACTUAL ---\n");
        sb.append(String.format("%-20s %-10s %-8s\n", "NOMBRE", "PRECIO", "STOCK"));
        sb.append("---------------------------------------\n");

        // Recorremos usando el ArrayList de nombres y el índice
        for (int i = 0; i < nombres.size(); i++) {
            String nombre = nombres.get(i);
            double precio = precios[i];
            int cantidad = stock.get(nombre); // Obtenemos el stock usando el nombre como clave
            
            // Formateo de salida para claridad (Criterio de Aceptación)
            sb.append(String.format("%-20s $%-9.2f %-8d\n", nombre, precio, cantidad));
        }
        
        TiendaVista.mostrarMensaje(sb.toString(), "Listado de Inventario");
    }

    /**
     * TAREA 3.3: Comprar producto.
     * Solicita producto y cantidad. Valida existencia y stock.
     */
    public void comprarProducto() {
    String nombre = TiendaVista.pedirString("Ingrese el nombre del producto a comprar:").trim();
    if (nombre == null || nombre.isEmpty()) return;

    // Mantenemos la validación de existencia aquí, pues el usuario puede escribir cualquier cosa
    if (!modelo.existeProducto(nombre)) {
        TiendaVista.mostrarError("El producto '" + nombre + "' no existe en el inventario.");
        return;
    }
    
    try {
        String cantidadStr = TiendaVista.pedirString("Ingrese la cantidad de " + nombre + " que desea comprar:");
        if (cantidadStr == null || cantidadStr.isEmpty()) return;
        int cantidad = Integer.parseInt(cantidadStr);

        if (cantidad <= 0) {
            TiendaVista.mostrarError("La cantidad debe ser un número positivo.");
            return;
        }

        double precio = modelo.getPrecio(nombre);
        double subtotal = precio * cantidad;
        
        String mensajeConfirmacion = String.format("Confirmar compra de %d unidades de %s por un total de $%.2f.",
                                                   cantidad, nombre, subtotal);
        
        if (TiendaVista.pedirConfirmacion(mensajeConfirmacion)) {
            
            // El Modelo lanza la excepción si el stock es insuficiente
            modelo.actualizarStock(nombre, cantidad); 
            modelo.setTotalVendido(modelo.getTotalVendido() + subtotal);
            
            TiendaVista.mostrarMensaje("✅ Compra realizada con éxito. Nuevo stock de " + nombre + ": " + 
                                      modelo.getStockProductos().get(nombre), "Compra Exitosa");
        } else {
            TiendaVista.mostrarMensaje("Compra cancelada.", "Información");
        }

    } catch (NumberFormatException e) {
        TiendaVista.mostrarError("ERROR de formato: La cantidad debe ser un número entero válido.");
    } catch (StockInsuficienteException e) {
        // Captura la excepción personalizada y muestra su mensaje
        TiendaVista.mostrarError(e.getMessage());
    }
}
    
    /**
     * TAREA 3.4: Mostrar estadísticas (más barato y más caro).
     * Encuentra precio mínimo y máximo recorriendo el array precios.
     */
    public void mostrarEstadisticas() {
        double[] precios = modelo.getPrecios();
        ArrayList<String> nombres = modelo.getNombresProductos();
        
        if (precios.length == 0) {
            TiendaVista.mostrarMensaje("No hay productos para mostrar estadísticas.", "Estadísticas");
            return;
        }

        double precioMin = Double.MAX_VALUE;
        String nombreMin = "";
        double precioMax = Double.MIN_VALUE;
        String nombreMax = "";

        // Recorrer el array de precios para encontrar el mínimo y el máximo
        for (int i = 0; i < precios.length; i++) {
            double precio = precios[i];
            
            if (precio < precioMin) {
                precioMin = precio;
                nombreMin = nombres.get(i);
            }
            
            if (precio > precioMax) {
                precioMax = precio;
                nombreMax = nombres.get(i);
            }
        }
        
        String resultado = String.format(
                "--- ESTADÍSTICAS DE PRECIOS ---\n" +
                "Producto más barato: %s ($%.2f)\n" +
                "Producto más caro: %s ($%.2f)",
                nombreMin, precioMin, nombreMax, precioMax
        );
        
        TiendaVista.mostrarMensaje(resultado, "Estadísticas");
    }

    /**
     * TAREA 3.5: Buscar producto por nombre.
     * Permite coincidencias parciales en el nombre.
     */
    public void buscarProducto() {
        String busqueda = TiendaVista.pedirString("Ingrese el texto a buscar en el nombre del producto:").trim();
        if (busqueda == null || busqueda.isEmpty()) return;
        
        String busquedaLowerCase = busqueda.toLowerCase();
        ArrayList<String> nombres = modelo.getNombresProductos();
        StringBuilder resultados = new StringBuilder();
        
        resultados.append("--- RESULTADOS DE BÚSQUEDA para '").append(busqueda).append("' ---\n");
        resultados.append(String.format("%-20s %-10s %-8s\n", "NOMBRE", "PRECIO", "STOCK"));
        resultados.append("---------------------------------------\n");
        
        int coincidencias = 0;
        
        // Recorremos la lista de nombres
        for (int i = 0; i < nombres.size(); i++) {
            String nombre = nombres.get(i);
            
            // Lógica de coincidencia parcial
            if (nombre.toLowerCase().contains(busquedaLowerCase)) {
                coincidencias++;
                
                double precio = modelo.getPrecios()[i];
                int stock = modelo.getStockProductos().get(nombre);
                
                resultados.append(String.format("%-20s $%-9.2f %-8d\n", nombre, precio, stock));
            }
        }
        
        if (coincidencias == 0) {
            TiendaVista.mostrarMensaje("No se encontraron coincidencias para '" + busqueda + "'.", "Búsqueda");
        } else {
            TiendaVista.mostrarMensaje(resultados.toString(), "Búsqueda Exitosa");
        }
    }
    
    /**
     * TAREA 3.6: Salir.
     * Muestra total acumulado de compras.
     */
    public void mostrarTicketFinal() {
        double total = modelo.getTotalVendido();
        String ticket = String.format("Gracias por usar MiniTienda Riwi.\n" +
                                      "-------------------------------------\n" +
                                      "TOTAL ACUMULADO DE VENTAS EN SESIÓN: $%.2f\n" +
                                      "-------------------------------------", total);
        TiendaVista.mostrarMensaje(ticket, "Ticket Final");
    }

    // Método para obtener el total vendido, útil para el Main
    public double getTotalVendido() {
        return modelo.getTotalVendido();
    }
}
