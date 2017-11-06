/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

/**
 *
 * @author oscar
 */
public class SistemaIntegradoDeSupermercado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // This is a testing main class without UI, Set FrontEnd.mainMenu as default
        // runing/main class to test the full application
        ArbolDeBusquedaBinaria b=new ArbolDeBusquedaBinaria();
        b.bd.Conectar("local","usuario","pass");
        /*
        b.insertar(10095, "1erelemento", 25, 1.65);b.insertar(10035, "2doelemento", 10, 2.55,false);
        b.insertar(10052, "3erelemento", 6, 5.99);b.insertar(10012, "4toelemento", 3, 10.20);
        b.insertar(10064, "5toelemento", 5, 9.75);b.insertar(10001, "Lambo Sesto Elemento", 1, 2920000);
        */
        b.display(b.root);
        //System.out.print(b.counter);
        b.bd.Desconectar();
    }
    
}
