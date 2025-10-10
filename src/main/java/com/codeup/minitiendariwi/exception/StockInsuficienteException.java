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
 * Excepción lanzada cuando no hay suficiente stock para completar una compra.
 */
public class StockInsuficienteException  extends Exception{
     // Constructor que recibe detalles para un mensaje de error preciso
    public StockInsuficienteException(String nombre, int stockDisponible, int cantidadSolicitada) {
        super(String.format("Stock insuficiente para '%s'. Disponible: %d, Solicitado: %d.", 
            nombre, stockDisponible, cantidadSolicitada));
    }
}
