/*
 * Trabajo Práctico Integrador – Programación 2
 * Food Store – Sistema de Gestión de Pedidos de Comida (Consola + JDBC)
 * Alumno: Franco Detarsio
 * Comisión 4
 */

package integrador_prog2;

import integrador_prog2.UI.MenuPrincipal;
import integrador_prog2.entities.Categoria;
import integrador_prog2.entities.Producto;
import integrador_prog2.entities.Usuario;
import integrador_prog2.services.ServiceCategoria;
import integrador_prog2.services.ServicePedido;
import integrador_prog2.services.ServiceProducto;
import integrador_prog2.services.ServiceUsuario;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Inicialización de Servicios
        ServiceCategoria sCat = new ServiceCategoria();
        ServiceProducto sProd = new ServiceProducto(sCat);
        ServiceUsuario sUsuario = new ServiceUsuario();
        ServicePedido sPedido = new ServicePedido(sUsuario, sProd);

        // inicialización de objetos para pruebas
        inicializarObjetosPrueba(sCat, sProd, sUsuario, sPedido);

        // Inicialización de Scanner
        Scanner sc = new Scanner(System.in);
        

        // *** Inicialización Menú ***
        MenuPrincipal.iniciarMenu(sc, sCat, sProd, sUsuario, sPedido);
         
        sc.close(); // Final de aplicación, se cierra scanner
        
    }
    
    // Método de Inicializacion de objetos para pruebas
    public static void inicializarObjetosPrueba(ServiceCategoria sCat, ServiceProducto sProd, ServiceUsuario sUsuario, ServicePedido sPedid)
            {
        // Categorías
        Categoria cat1 = new Categoria("Bebidas", "Productos líquidos destinados al consumo");
        Categoria cat2 = new Categoria("Carnes", "Productos cárnicos frescos o procesados");
        
        sCat.getCategorias().add(cat1);
        sCat.getCategorias().add(cat2);
        
        
        // Productos
        Producto Prod1 = new Producto("Coca Cola", 1500, "Regular 250cc", 25, "ImagenCoca250cc", true, cat1);
        Producto Prod2 = new Producto("Paty Clásica", 2000, "Medallón de carne - chico", 10, "ImagenPatyClasica", true, cat2);
        
        sProd.getProductos().add(Prod1);
        sProd.getProductos().add(Prod2);
        
        
        // Usuarios
        Usuario u1 = new Usuario("Franco", "Detarsio", "detarsiolp@outlook.com", "341555999");
        Usuario u2 = new Usuario("Katerina", "Gerger", "Katy@icloud.com", "341253546");
        
        sUsuario.getUsuarios().add(u1);
        sUsuario.getUsuarios().add(u2);
    }

}
