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
public class BaseDeDatos {
    public Connection conexion;
    
    public boolean Conectar(String url, String user, String pass){
        String urls=new String("local");
        if(urls.equals(url)){
            try {
            conexion=DriverManager.getConnection("jdbc:derby:base");
            return true;
            } catch (SQLException ex) {
                Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conexion=DriverManager.getConnection("jdbc:derby:base;create=true");
                    String sentenciaSQL="create table ITEMS(ITEMID CHAR(5) NOT NULL, ITEMNAME VARCHAR(30) NOT NULL, ITEMQUANT NUMERIC(8,2) NOT NULL,ITEMPRICE NUMERIC(16,2) NOT NULL)";
                    PreparedStatement statement=conexion.prepareStatement(sentenciaSQL);
                    statement.execute();
                    return true;
                } catch (SQLException ex1) {
                    Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex1);
                    return false;
                }
            }
        }
        else{
            try {
                conexion=DriverManager.getConnection("jdbc:postgresql:"+url+"/base", user, pass);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
                return false;
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
    
    public boolean insertarEnBase(Integer itemID, String itemName, Integer itemQuant, double itemPrice){
        try {
                String sentenciaSQL="INSERT INTO ITEMS(ITEMID, ITEMNAME, ITEMQUANT, ITEMPRICE) VALUES"+"(?,?,?,?)";
                PreparedStatement preparedStatement;
                preparedStatement = conexion.prepareStatement(sentenciaSQL);
                preparedStatement.setInt(1, itemID);
                preparedStatement.setString(2, itemName);
                preparedStatement.setInt(3, itemQuant);
                preparedStatement.setDouble(4, itemPrice);
                preparedStatement.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ArbolDeBusquedaBinaria.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
    }
    
    public boolean removerEnBase(Integer itemID){
        try {
            String sentenciaSQL="DELETE FROM ITEMS WHERE ITEMID = ?";
            PreparedStatement preparedStatement;
            preparedStatement = conexion.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, itemID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
