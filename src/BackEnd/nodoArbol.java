/*
 * Copyright (C) 2017 oscar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package BackEnd;

/**
 *
 * @author oscar
 */
public class nodoArbol {
    
    private Integer itemID;
    private String itemName;
    private Integer itemQuant;
    private double itemPrice;
    private nodoArbol izquierdo;
    private nodoArbol derecho;

    public nodoArbol(Integer itemID, String itemName, Integer itemQuant, double itemPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuant = itemQuant;
        this.itemPrice = itemPrice;
        //this.izquierdo = izquierdo;
        //this.derecho = derecho;
    }
    

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemQuant() {
        return itemQuant;
    }

    public void setItemQuant(Integer itemQuant) {
        this.itemQuant = itemQuant;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public nodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(nodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public nodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(nodoArbol derecho) {
        this.derecho = derecho;
    }

    @Override
    public String toString() {
        return "nodoArbol{\n" + "itemID=" + itemID + "\nitemName=" + itemName + "\nitemPrice=" + itemPrice + '}'+"\n\n";
    }
}
