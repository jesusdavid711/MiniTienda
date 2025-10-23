/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.minitiendariwi.service;

import com.codeup.minitiendariwi.domain.Alimento;
import com.codeup.minitiendariwi.domain.Electrodomestico;
import com.codeup.minitiendariwi.domain.InventarioModelo;
import com.codeup.minitiendariwi.ui.TiendaVista;
import com.codeup.minitiendariwi.exception.ProductoYaExisteException;
import com.codeup.minitiendariwi.exception.StockInsuficienteException;
import com.codeup.minitiendariwi.domain.Producto;
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
    // Instancia del Modelo para acceder y manipular los datos.
    private final InventarioModelo modelo;

    /**
     * Constructor. Inicializa el Modelo con algunos datos de prueba.
     */
    public TiendaServicio() {
        this.modelo = new InventarioModelo();

        try {
            // Productos de ejemplo usando POO
            Producto p1 = new Electrodomestico("Portátil Riwi", 1200.00);
            Producto p2 = new Electrodomestico("Teclado Mecánico", 85.50);
            Producto p3 = new Alimento("Café Colombiano", 15.75);
            Producto p4 = new Alimento("Pan Integral", 3.20);

            modelo.addProducto(p1, 5);
            modelo.addProducto(p2, 10);
            modelo.addProducto(p3, 20);
            modelo.addProducto(p4, 30);

        } catch (ProductoYaExisteException e) {
            System.err.println("Advertencia al inicializar datos: " + e.getMessage());
        }
    }

    // -----------------------------------------------------
    // OPCIÓN 1: Agregar producto
    // -----------------------------------------------------
    public void agregarProducto() {
        String tipo = TiendaVista.pedirString("Ingrese el tipo de producto (1. Alimento / 2. Electrodoméstico):");
        if (tipo == null || tipo.isEmpty()) return;

        String nombre = TiendaVista.pedirString("Ingrese el nombre del nuevo producto:").trim();
        if (nombre.isEmpty()) {
            TiendaVista.mostrarError("El nombre del producto no puede estar vacío.");
            return;
        }

        try {
            double precio = Double.parseDouble(TiendaVista.pedirString("Ingrese el precio unitario de " + nombre + ":"));
            int stock = Integer.parseInt(TiendaVista.pedirString("Ingrese la cantidad inicial de stock de " + nombre + ":"));

            if (precio <= 0 || stock < 0) {
                TiendaVista.mostrarError("El precio debe ser positivo y el stock no puede ser negativo.");
                return;
            }

            Producto nuevoProducto;

            // Crear la instancia según el tipo ingresado
            if (tipo.equals("1")) {
                nuevoProducto = new Alimento(nombre, precio);
            } else if (tipo.equals("2")) {
                nuevoProducto = new Electrodomestico(nombre, precio);
            } else {
                TiendaVista.mostrarError("Tipo no válido. Debe ser 1 o 2.");
                return;
            }

            modelo.addProducto(nuevoProducto, stock);
            TiendaVista.mostrarMensaje("✅ Producto '" + nombre + "' agregado exitosamente.", "Éxito");

        } catch (NumberFormatException e) {
            TiendaVista.mostrarError("ERROR de formato: El precio y el stock deben ser números válidos.");
        } catch (ProductoYaExisteException e) {
            TiendaVista.mostrarError(e.getMessage());
        }
    }


    // -----------------------------------------------------
    // OPCIÓN 2: Listar inventario
    // -----------------------------------------------------
    public void listarInventario() {
        ArrayList<Producto> productos = modelo.getProductos();
        HashMap<String, Integer> stock = modelo.getStockProductos();

        if (productos.isEmpty()) {
            TiendaVista.mostrarMensaje("El inventario está vacío.", "Inventario");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- INVENTARIO ACTUAL ---\n");
        sb.append(String.format("%-20s %-10s %-8s %-40s\n", "NOMBRE", "PRECIO", "STOCK", "DESCRIPCIÓN"));
        sb.append("-----------------------------------------------------------------------------\n");

        for (Producto p : productos) {
            String nombre = p.getNombre();
            double precio = p.getPrecio();
            int cantidad = stock.get(nombre);
            String descripcion = p.getDescripcion();

            sb.append(String.format("%-20s $%-9.2f %-8d %-40s\n", nombre, precio, cantidad, descripcion));
        }

        TiendaVista.mostrarMensaje(sb.toString(), "Listado de Inventario");
    }

    // -----------------------------------------------------
    // OPCIÓN 3: Comprar producto
    // -----------------------------------------------------
    public void comprarProducto() {
        String nombre = TiendaVista.pedirString("Ingrese el nombre del producto a comprar:").trim();
        if (nombre == null || nombre.isEmpty()) return;

        if (!modelo.existeProducto(nombre)) {
            TiendaVista.mostrarError("El producto '" + nombre + "' no existe en el inventario.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(TiendaVista.pedirString("Ingrese la cantidad de " + nombre + " que desea comprar:"));
            if (cantidad <= 0) {
                TiendaVista.mostrarError("La cantidad debe ser un número positivo.");
                return;
            }

            double precio = modelo.getPrecio(nombre);
            double subtotal = precio * cantidad;

            String mensajeConfirmacion = String.format(
                    "Confirmar compra de %d unidades de %s por un total de $%.2f.",
                    cantidad, nombre, subtotal);

            if (TiendaVista.pedirConfirmacion(mensajeConfirmacion)) {
                modelo.actualizarStock(nombre, cantidad);
                modelo.setTotalVendido(modelo.getTotalVendido() + subtotal);

                TiendaVista.mostrarMensaje("✅ Compra realizada con éxito. Nuevo stock de " +
                        nombre + ": " + modelo.getStockProductos().get(nombre), "Compra Exitosa");
            } else {
                TiendaVista.mostrarMensaje("Compra cancelada.", "Información");
            }

        } catch (NumberFormatException e) {
            TiendaVista.mostrarError("ERROR de formato: La cantidad debe ser un número entero válido.");
        } catch (StockInsuficienteException e) {
            TiendaVista.mostrarError(e.getMessage());
        }
    }

    // -----------------------------------------------------
    // OPCIÓN 4: Mostrar estadísticas
    // -----------------------------------------------------
    public void mostrarEstadisticas() {
        double[] precios = modelo.getPrecios();
        ArrayList<Producto> productos = modelo.getProductos();

        if (precios.length == 0) {
            TiendaVista.mostrarMensaje("No hay productos para mostrar estadísticas.", "Estadísticas");
            return;
        }

        double precioMin = Double.MAX_VALUE;
        String nombreMin = "";
        double precioMax = Double.MIN_VALUE;
        String nombreMax = "";

        for (int i = 0; i < precios.length; i++) {
            double precio = precios[i];
            Producto p = productos.get(i);

            if (precio < precioMin) {
                precioMin = precio;
                nombreMin = p.getNombre();
            }

            if (precio > precioMax) {
                precioMax = precio;
                nombreMax = p.getNombre();
            }
        }

        String resultado = String.format(
                "--- ESTADÍSTICAS DE PRECIOS ---\n" +
                "Producto más barato: %s ($%.2f)\n" +
                "Producto más caro: %s ($%.2f)",
                nombreMin, precioMin, nombreMax, precioMax);

        TiendaVista.mostrarMensaje(resultado, "Estadísticas");
    }

    // -----------------------------------------------------
    // OPCIÓN 5: Buscar producto
    // -----------------------------------------------------
    public void buscarProducto() {
        String busqueda = TiendaVista.pedirString("Ingrese el texto a buscar en el nombre del producto:").trim();
        if (busqueda == null || busqueda.isEmpty()) return;

        String busquedaLowerCase = busqueda.toLowerCase();
        ArrayList<Producto> productos = modelo.getProductos();
        StringBuilder resultados = new StringBuilder();

        resultados.append("--- RESULTADOS DE BÚSQUEDA para '").append(busqueda).append("' ---\n");
        resultados.append(String.format("%-20s %-10s %-8s %-40s\n", "NOMBRE", "PRECIO", "STOCK", "DESCRIPCIÓN"));
        resultados.append("-----------------------------------------------------------------------------\n");

        int coincidencias = 0;

        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(busquedaLowerCase)) {
                coincidencias++;
                double precio = p.getPrecio();
                int stock = modelo.getStockProductos().get(p.getNombre());
                String descripcion = p.getDescripcion();

                resultados.append(String.format("%-20s $%-9.2f %-8d %-40s\n",
                        p.getNombre(), precio, stock, descripcion));
            }
        }

        if (coincidencias == 0) {
            TiendaVista.mostrarMensaje("No se encontraron coincidencias para '" + busqueda + "'.", "Búsqueda");
        } else {
            TiendaVista.mostrarMensaje(resultados.toString(), "Búsqueda Exitosa");
        }
    }

    // -----------------------------------------------------
    // OPCIÓN 6: Salir y mostrar ticket final
    // -----------------------------------------------------
    public void mostrarTicketFinal() {
        double total = modelo.getTotalVendido();
        String ticket = String.format("Gracias por usar MiniTienda Riwi.\n" +
                "-------------------------------------\n" +
                "TOTAL ACUMULADO DE VENTAS EN SESIÓN: $%.2f\n" +
                "-------------------------------------", total);
        TiendaVista.mostrarMensaje(ticket, "Ticket Final");
    }

    public double getTotalVendido() {
        return modelo.getTotalVendido();
    }
}
