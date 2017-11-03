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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar
 */
public class ArbolDeBusquedaBinaria {
    
    public nodoArbol root;
    private Connection conexion;
    
    public ArbolDeBusquedaBinaria() {
        this.root=null;
    }

    public void Conectar(){
        try {
            conexion=DriverManager.getConnection("jdbc:derby:itemsDB");
        } catch (SQLException ex) {
            Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conexion=DriverManager.getConnection("jdbc:derby:itemsDB;create=true");
                String sentenciaSQL="create table ITEMS(ITEMID CHAR(5) NOT NULL, ITEMNAME VARCHAR(30) NOT NULL, ITEMQUANT NUMERIC(8,2) NOT NULL,ITEMPRICE NUMERIC(16,2) NOT NULL)";
                PreparedStatement statement=conexion.prepareStatement(sentenciaSQL);
                statement.execute();
            } catch (SQLException ex1) {
                Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void Desconectar(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean busqueda(Integer itemID){
        nodoArbol actual=root;
        while(actual!=null){
            if(actual.getItemID()==itemID){
                return true;
            }else if(actual.getItemID()>itemID){
                actual=actual.getIzquierdo();
            }else{
                actual=actual.getDerecho();
            }
        }
        return false;
    }
    
    public boolean remover(Integer itemID){
        nodoArbol padre=root;
        nodoArbol actual=root;
        boolean esIzquierdo=false;
        while(actual.getItemID()!=itemID){
            padre=actual;
            if(actual.getItemID()>itemID){
                esIzquierdo=true;
                actual=actual.getIzquierdo();
            }else{
                esIzquierdo=false;
                actual=actual.getDerecho();
            }
            if(actual==null){
                return false;
            }
        }
        //Caso 1: Es nodo hoja
        if(actual.getIzquierdo()==null && actual.getDerecho()==null){
            if(actual==root){
                root=null;
                //String sentencia="DELETE FROM ITEMS ";
            }
            if(esIzquierdo==true){
                padre.setIzquierdo(null);
            }else{
                padre.setDerecho(null);
            }
        }
        
        else if(actual.getDerecho()==null){
            if(actual==root){
                root=actual.getDerecho();
            }else if(esIzquierdo){
                padre.setIzquierdo(actual.getDerecho());
            }else{
                padre.setDerecho(actual.getDerecho());
            }
        }else if(actual.getIzquierdo()!=null && actual.getDerecho()!=null){
            nodoArbol sucesor=getSucesor(actual);
            if(actual==root){
                root=sucesor;
            }else if(esIzquierdo){
                padre.setIzquierdo(sucesor);
            }else{
                padre.setDerecho(sucesor);
            }
            sucesor.setIzquierdo(actual.getIzquierdo());
        }
        return true;
    }
    
    public nodoArbol getSucesor(nodoArbol nodo){
        nodoArbol sucesor=null;
        nodoArbol padreDeSucesor=null;
        nodoArbol actual=nodo.getDerecho();
        while(actual!=null){
            padreDeSucesor=sucesor;
            sucesor=actual;
            actual=actual.getIzquierdo();
        }
        
        if(sucesor!=nodo.getDerecho()){
            padreDeSucesor.setIzquierdo(sucesor.getDerecho());
            sucesor.setDerecho(nodo.getDerecho());
        }
        return sucesor;
    }
    
    public void insertar(Integer itemID, String itemName, Integer itemQuant, double itemPrice){
        nodoArbol nuevoNodo= new nodoArbol(itemID, itemName, itemQuant, itemPrice);
        if(root==null){
            root=nuevoNodo;
            //insercion en DB
            try {
                String sentenciaSQL="INSERT INTO ITEMS(ITEMID, ITEMNAME, ITEMQUANT, ITEMPRICE) VALUES"+"(?,?,?,?)";
                PreparedStatement preparedStatement;
                preparedStatement = conexion.prepareStatement(sentenciaSQL);
                preparedStatement.setInt(1, nuevoNodo.getItemID());
                preparedStatement.setString(2, nuevoNodo.getItemName());
                preparedStatement.setInt(3, nuevoNodo.getItemQuant());
                preparedStatement.setDouble(4, nuevoNodo.getItemPrice());
                preparedStatement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        nodoArbol actual=root;
        nodoArbol padre=null;
        while(true){
            padre=actual;
            if(itemID<actual.getItemID()){
                actual=actual.getIzquierdo();
                if(actual==null){
                    padre.setIzquierdo(nuevoNodo);
                    try {
                        String sentenciaSQL="INSERT INTO ITEMS(ITEMID, ITEMNAME, ITEMQUANT, ITEMPRICE) VALUES"+"(?,?,?,?)";
                        PreparedStatement preparedStatement=conexion.prepareStatement(sentenciaSQL);
                        preparedStatement.setInt(1, nuevoNodo.getItemID());
                        preparedStatement.setString(2, nuevoNodo.getItemName());
                        preparedStatement.setInt(3, nuevoNodo.getItemQuant());
                        preparedStatement.setDouble(4, nuevoNodo.getItemPrice());
                        preparedStatement.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            }else{
                actual=actual.getDerecho();
                if(actual==null){
                    padre.setDerecho(nuevoNodo);
                    try {
                        String sentenciaSQL="INSERT INTO ITEMS(ITEMID, ITEMNAME, ITEMQUANT, ITEMPRICE) VALUES"+"(?,?,?,?)";
                        PreparedStatement preparedStatement=conexion.prepareStatement(sentenciaSQL);
                        preparedStatement.setInt(1, nuevoNodo.getItemID());
                        preparedStatement.setString(2, nuevoNodo.getItemName());
                        preparedStatement.setInt(3, nuevoNodo.getItemQuant());
                        preparedStatement.setDouble(4, nuevoNodo.getItemPrice());
                        preparedStatement.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
            }
        }
    }
    
    public void display(nodoArbol root){
        if(root!=null){
            display(root.getIzquierdo());
            System.out.print(" "+ root.toString());
            display(root.getDerecho());
        }
    }
}
