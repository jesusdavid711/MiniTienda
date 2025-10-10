/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.minitiendariwi.exception;

/**
 *
 * @author Coder
 */
/**
 * Excepción lanzada cuando se intenta agregar un producto
 * cuyo nombre ya existe en el inventario.
 */
public class ProductoYaExisteException extends Exception {
    // Constructor que recibe el nombre y crea un mensaje descriptivo
    public ProductoYaExisteException(String nombre) {
        super("El producto '" + nombre + "' ya existe en el inventario.");
    }
}
