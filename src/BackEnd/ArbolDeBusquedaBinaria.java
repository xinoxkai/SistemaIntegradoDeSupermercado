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

import FrontEnd.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author oscar
 */
public class ArbolDeBusquedaBinaria {
    
    public nodoArbol root, auxNode;
    public BaseDeDatos bd=new BaseDeDatos();
    
    public ArbolDeBusquedaBinaria() {
        this.root=null;
    }
    
    public boolean busqueda(Integer itemID){
        nodoArbol actual=root;
        while(actual!=null){
            if(Objects.equals(actual.getItemID(), itemID)){
                auxNode=actual;
                return true;
            }else if(actual.getItemID()>itemID){
                actual=actual.getIzquierdo();
            }else{
                actual=actual.getDerecho();
            }
        }
        return false;
    }
    
    public boolean eliminar(Integer itemID){
        nodoArbol padre=root;
        nodoArbol actual=root;
        boolean esIzquierdo=false;
        while(!Objects.equals(actual.getItemID(), itemID)){
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
                bd.eliminarEnBase(actual.getItemID());//Removiendo articulo de BD
            }
            if(esIzquierdo==true){
                padre.setIzquierdo(null);
            }else{
                padre.setDerecho(null);
            }
        }
        //Caso 2 : Nodo tiene solo un hijo
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
    
    public boolean insertar(Integer itemID, String itemName, Integer itemQuant, Double itemPrice){
        nodoArbol nuevoNodo= new nodoArbol(itemID, itemName, itemQuant, itemPrice);
        if(root==null){
            root=nuevoNodo;
            return bd.insertarEnBase(itemID, itemName, itemQuant, itemPrice);
        }
        nodoArbol actual=root;
        nodoArbol padre=null;
        while(true){
            padre=actual;
            if(itemID<actual.getItemID()){
                actual=actual.getIzquierdo();
                if(actual==null){
                    padre.setIzquierdo(nuevoNodo);
                    return bd.insertarEnBase(itemID, itemName, itemQuant, itemPrice);
                    
                }
            }else{
                actual=actual.getDerecho();
                if(actual==null){
                    padre.setDerecho(nuevoNodo);
                    return bd.insertarEnBase(itemID, itemName, itemQuant, itemPrice);
                }
            }
        }
    }
    
    public void insertar(Integer itemID, String itemName, Integer itemQuant, Double itemPrice, Boolean init){
        nodoArbol nuevoNodo= new nodoArbol(itemID, itemName, itemQuant, itemPrice);
        if(root==null){
            root=nuevoNodo;
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
                    return;
                    
                }
            }else{
                actual=actual.getDerecho();
                if(actual==null){
                    padre.setDerecho(nuevoNodo);
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
    
    public Integer itemCount(nodoArbol root){
        if(root==null){
            return 0;
        }
        else{
            return (itemCount(root.getIzquierdo())+1+itemCount(root.getDerecho()));
        }
    }
    
}
