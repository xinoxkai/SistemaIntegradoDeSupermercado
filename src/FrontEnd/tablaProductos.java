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

import BackEnd.ArbolDeBusquedaBinaria;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author oscar
 */

public class tablaProductos extends AbstractTableModel{
    
    ArbolDeBusquedaBinaria b=new ArbolDeBusquedaBinaria();
    
    public tablaProductos(ArbolDeBusquedaBinaria arbol) {
        b=arbol;
    }
    
    

    
    Object valor=null;
    
    @Override
    public int getRowCount() {
        return b.itemCount(b.root);
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
            switch(columnIndex){
                case 0: valor=b.auxNode.getItemID();
                        break;
                case 1: valor=b.auxNode.getItemName();
                        break;
                case 2: valor=b.auxNode.getItemQuant();
                        break;
                case 3: valor=b.auxNode.getItemPrice();
            }
            return valor;
    }
    
}
