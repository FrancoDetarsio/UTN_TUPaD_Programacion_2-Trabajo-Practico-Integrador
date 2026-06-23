package integrador_prog2.UI;

import integrador_prog2.entities.Categoria;
import integrador_prog2.entities.Pedido;
import integrador_prog2.entities.Producto;
import integrador_prog2.entities.Usuario;
import integrador_prog2.exception.RespuestaInvalidaException;
import java.util.List;
import java.util.Scanner;

public class MenuBase {

    
    /**
     * Muestra un submenú para cada seleccion del menú principal con opciones
     * CRUD
     * @param titulo
     */
    public static void mostrarSubMenu(String titulo) {
        System.out.println("\n ======== " + titulo + " ========");
        System.out.println("|                            |");
        System.out.println("|       1 - Listar           |");
        System.out.println("|       2 - Crear            |");
        System.out.println("|       3 - Editar           |");
        System.out.println("|       4 - Eliminar         |");
        System.out.println("|                            |");
        System.out.println("==============================");
        System.out.print("Seleccion: ");
    }
    
    // Métodos de Lectura
    public static int leerInt(Scanner sc) {
        return Integer.parseInt(sc.nextLine());
    }

    public static double leerDouble(Scanner sc) {
        return Double.parseDouble(sc.nextLine());
    }

    /**
     * Método para leer un Long
     *
     * @param sc
     * @return Long si la entrada es correcta, null si no lo es
     */
    public static Long leerLong(Scanner sc) {
        try {
            return Long.valueOf(sc.nextLine());
        } catch (Exception e) {
            return null;
        }
    }
    
    
    // Validaciónes
    
    /**
     * valida la opción inicial común a cada menú, si se ingresa un campo
     * invalido se devuelve -1 (invóca el case default.
     * @param sc
     * @return 
     */
    public static int validarOpcion(Scanner sc) {
        try {
            return leerInt(sc);
        } catch (NumberFormatException e) {
            System.out.print("Error: ");
            return -1;
        }
    }
    
    /**
     * valida respuestas de si o no mediante "s"/"n"
     * si es afirmativo devuelve true
     * si es negativo o ingresa un valor inválido devuelve false
     * confirma cualquiera de las opciones mostrando mensaje por pantalla
     * @param respuesta
     * @return boolean
     */
    public static boolean validarRespuestaPrint(String respuesta) {
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.println("operación realizada.");
            return true;

        } else if (respuesta.equalsIgnoreCase("N")) {
            System.out.println("Operación cancelada.");
        } else {
            System.out.println("Respuesta inválida: La operación se canceló.");
        }
        return false;
    }
    
    /**
     * valida respuestas de si o no mediante "s"/"n"
     * si es afirmativo devuelve true
     * si es negativo devuelve false
     * si el parámetro es iválido se lanza excepcion
     * @param respuesta
     * @return boolean
     * @throws integrador_prog2.exception.RespuestaInvalidaException
     */
    public static boolean validarRespuesta(String respuesta) throws RespuestaInvalidaException {
        if (respuesta.equalsIgnoreCase("S")) {
            return true;
        } else if (respuesta.equalsIgnoreCase("N")) {
            return false;
        }
        throw new RespuestaInvalidaException("Se debe ingresár \"S\" o \"N\" para definir la disponibilidad.");
    }
    
    
    // Métodos para imprimir Listas
    
    /**
     * Recibe una lista de categorías por parámetro e imprime sus elementos, si
     * la lista esta vacía se avisa por pantalla.
     * @param listaCat 
     */
    public static void imprimirListaCategorias(List<Categoria> listaCat) {
        if (!listaCat.isEmpty()) {
            for (Categoria cat : listaCat) {
                System.out.println(cat);
            }
        } else {
            System.out.println("No hay categorías disponibles.");
        }
    }
    
    /**
     * Recibe una lista de productos por parámetro e imprime sus elementos, si
     * la lista esta vacía se avisa por pantalla.
     *
     * @param listaprod
     */
    public static void imprimirListaProductos(List<Producto> listaprod) {
        if (!listaprod.isEmpty()) {
            for (Producto p : listaprod) {
                System.out.println(" - " + p);
            }
        } else {
            System.out.println("No hay Productos disponibles.");
        }
    }

    /**
     * Recibe una lista de usuarios por parámetro e imprime sus elementos, si la
     * lista esta vacía se avisa por pantalla.
     *
     * @param listaUsuarios
     */
    public static void imprimirListaUsuarios(List<Usuario> listaUsuarios) {
        if (!listaUsuarios.isEmpty()) {
            for (Usuario user : listaUsuarios) {
                System.out.println(" - " + user);
            }
        } else {
            System.out.println("No hay usuarios disponibles.");
        }
    }

    /**
     * Recibe una lista de pedidos por parámetro e imprime sus elementos, si la
     * lista esta vacía se avisa por pantalla.
     *
     * @param listaPedidos
     */
    public static void imprimirListaPedidos(List<Pedido> listaPedidos) {
        if (!listaPedidos.isEmpty()) {
            for (Pedido p : listaPedidos) {
                System.out.println(p);
            }
        } else {
            System.out.println("No hay Pedidos disponibles.");
        }
    }
    
    /**
     * Recibe lista de categorias y de productos, imprime por pantalla los productos 
     * por categoría, si no se encuentran productos se notifica por pantalla.
     * @param listaCat
     * @param listaProd 
     */
    public static void imprimirProductosXCategoria(List<Categoria> listaCat, List<Producto> listaProd) {
        if (!listaProd.isEmpty()) {
            for (Categoria cat : listaCat) {
                System.out.println("Categoría: " + cat.getNombre());
                for (Producto p : listaProd) {
                    if (p.getCategoria().equals(cat) && !p.isEliminado()) {
                        System.out.println(" - " + p);
                    }
                }
            }
        } else {
            System.out.println("No hay productos disponibles.");
        }
    }
    
}
