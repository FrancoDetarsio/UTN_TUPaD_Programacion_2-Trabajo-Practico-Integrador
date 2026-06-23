package integrador_prog2.UI;

import integrador_prog2.services.ServiceCategoria;
import integrador_prog2.services.ServicePedido;
import integrador_prog2.services.ServiceProducto;
import integrador_prog2.services.ServiceUsuario;
import java.util.Scanner;

public class MenuPrincipal extends MenuBase{
    
    public static void iniciarMenu(Scanner sc, ServiceCategoria sCat, ServiceProducto sProd, ServiceUsuario sUsuario, ServicePedido sPedido) {
        
        // === Interfáz de Consola ===
        System.out.println("\n********* Food Store *********\n");
        
        int opcion = -1; // variable para manejar manú con valor para iniciar el bucle
        
        do {
            // Menú Gráfico
            System.out.println("===== Sistema De Pedidos =====");
            System.out.println("|                            |");
            System.out.println("|       1 - Categorías       |");
            System.out.println("|       2 - Productos        |");
            System.out.println("|       3 - Usuarios         |");
            System.out.println("|       4 - Pedidos          |");
            System.out.println("|       0 - Salir            |");
            System.out.println("|                            |");
            System.out.println("==============================");
            System.out.print("Seleccion: ");
            
            opcion = validarOpcion(sc);

            switch (opcion) {
                case 1:
                    MenuCategoria.iniciarMenu(sc, sCat);
                    break;

                case 2:
                    MenuProducto.iniciarMenu(sc, sProd);
                    break;

                case 3:
                    MenuUsuario.iniciarMenu(sc, sUsuario);
                    break;

                case 4:
                    MenuPedido.iniciarMenu(sc, sPedido);
                    break;

                case 0:
                    System.out.println("Hasta Luego!");
                    break;

                default:
                    System.out.println("Ingrese un número de (0 - 4)");
            }

            System.out.println("");

        } while (opcion != 0);

    }
}