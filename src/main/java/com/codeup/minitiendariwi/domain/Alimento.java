/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.minitiendariwi.domain;

/**
 *
 * @author Coder
 */
public class Alimento extends Producto {
    public Alimento(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public String getDescripcion() {
        return "Alimento - Producto perecedero o de consumo diario.";
    }
}
