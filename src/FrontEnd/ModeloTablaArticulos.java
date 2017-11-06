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
package FrontEnd;

import BackEnd.nodoArbol;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author oscar
 */

public class ModeloTablaArticulos extends AbstractTableModel{
    
    List<nodoArbol> articulos=new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return articulos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        nodoArbol nodo=articulos.get(rowIndex);
        Object valor=null;
        switch(columnIndex){
                case 0: valor=nodo.getItemID();
                        break;
                case 1: valor=nodo.getItemName();
                        break;
                case 2: valor=nodo.getItemQuant();
                        break;
                case 3: valor=nodo.getItemPrice();
            }
            return valor;
    }
    
}
