/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.codeup.minitiendariwi;

import com.codeup.minitiendariwi.service.TiendaServicio;
import com.codeup.minitiendariwi.ui.TiendaVista;

/**
 *
 * @author Coder
 */
public class MiniTiendaRiwi {

    public static void main(String[] args) {
        
        // 1. Instanciar el Controlador/Servicio
        TiendaServicio servicio = new TiendaServicio();
        
        boolean continuar = true;

        // Bucle repetitivo para el menú principal
        while (continuar) {
            
            String opcion = TiendaVista.mostrarMenuYObtenerOpcion();
            
            if (opcion == null) {
                opcion = "6"; 
            }

            try {
                // Tarea 4: Manejo de excepciones (NumberFormatException) en la opción
                int opcionNum = Integer.parseInt(opcion.trim());
                
                switch (opcionNum) {
                    case 1:
                        servicio.agregarProducto(); // Llamada al Controlador
                        break;
                    case 2:
                        servicio.listarInventario(); // Llamada al Controlador
                        break;
                    case 3:
                        servicio.comprarProducto(); // Llamada al Controlador
                        break;
                    case 4:
                        servicio.mostrarEstadisticas(); // Llamada al Controlador
                        break;
                    case 5:
                        servicio.buscarProducto(); // Llamada al Controlador
                        break;
                    case 6:
                        servicio.mostrarTicketFinal(); // Llamada al Controlador
                        continuar = false;
                        break;
                    default:
                        TiendaVista.mostrarError("Opción no válida. Por favor, ingrese un número del 1 al 6.");
                }
            } catch (NumberFormatException e) {
                TiendaVista.mostrarError("Entrada no válida. Por favor, ingrese un número del 1 al 6.");
            }
        }
    }
}
