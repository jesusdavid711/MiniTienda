/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.minitiendariwi.ui;

import javax.swing.JOptionPane;

/**
 *
 * @author Coder
 */
/**
 * Clase que representa la Vista (V) en el patrón MVC.
 * Se encarga de toda la interacción con el usuario mediante JOptionPane.
 */
public class TiendaVista {
    
    // Constantes para las opciones del menú
    private static final String OPCIONES_MENU = 
            "*** MiniTienda Riwi - Menú Principal ***\n" +
            "1. Agregar producto\n" +
            "2. Listar inventario\n" +
            "3. Comprar producto\n" +
            "4. Mostrar estadísticas (más barato y más caro)\n" +
            "5. Buscar producto por nombre\n" +
            "6. Salir con ticket final\n\n" +
            "Ingrese su opción:";

    /**
     * Muestra el menú principal y pide una opción al usuario.
     * @return El número de la opción seleccionada como String (para validación posterior).
     */
    public static String mostrarMenuYObtenerOpcion() {
        // Utilizamos showInputDialog para mostrar el menú y capturar la entrada.
        return JOptionPane.showInputDialog(null, OPCIONES_MENU, "Menú de Opciones", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Muestra un mensaje de información al usuario.
     * @param mensaje El texto a mostrar.
     * @param titulo El título de la ventana.
     */
    public static void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de error al usuario.
     * @param mensaje El texto de error a mostrar.
     */
    public static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }
    
    // -------------------------------------------------------------------
    // Métodos para solicitar datos (se usarán en la Tarea 3)
    // -------------------------------------------------------------------
    
    /**
     * Pide un String al usuario (e.g., nombre del producto).
     * @param mensaje El mensaje a mostrar.
     * @return El String introducido.
     */
    public static String pedirString(String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje, "Entrada de Datos", JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Pide una confirmación al usuario (Sí/No).
     * @param mensaje El mensaje de confirmación.
     * @return true si selecciona 'Sí', false si selecciona 'No' o cierra.
     */
    public static boolean pedirConfirmacion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, 
                "Confirmar Acción", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
}
