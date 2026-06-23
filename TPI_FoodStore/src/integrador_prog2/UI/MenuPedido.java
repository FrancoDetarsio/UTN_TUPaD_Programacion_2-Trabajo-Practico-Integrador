package integrador_prog2.UI;

import integrador_prog2.entities.DetallePedido;
import integrador_prog2.entities.Pedido;
import integrador_prog2.entities.Producto;
import integrador_prog2.entities.Usuario;
import integrador_prog2.enums.Estado;
import integrador_prog2.enums.FormaPago;
import integrador_prog2.exception.EntidadNoEncontradaException;
import integrador_prog2.exception.PedidoNoEditableException;
import integrador_prog2.exception.ProductoInexistenteException;
import integrador_prog2.exception.RespuestaInvalidaException;
import integrador_prog2.exception.StockInvalidoException;
import integrador_prog2.services.ServicePedido;
import java.util.Scanner;

public class MenuPedido extends MenuBase {

    public static void iniciarMenu(Scanner sc, ServicePedido sPedido) {
        mostrarSubMenu("Pedidos");

        // variable para manejar menú con validación
        int opcion = validarOpcion(sc);

        switch (opcion) {
            case 1: // Listar
                listarPedidos(sPedido);
                break;

            case 2: // Crear
                crearPedidos(sc, sPedido);
                break;

            case 3: // Editar
                editarPedido(sc, sPedido);
                break;

            case 4: // Eliminar
                eliminarPedido(sc, sPedido);
                break;

            default:
                System.out.println("Debe Ingresar un número de (1 - 4)\nIntente Nuevamente");
        }
    }


    // opciónes del menú    
    private static void listarPedidos(ServicePedido sPedido) {
        System.out.println("\n== Listar Pedidos ==");
        
        if (!sPedido.listaPedidosDisponibles().isEmpty()) {
            System.out.println("===================================================================================");
            for (Pedido ped : sPedido.listaPedidosDisponibles()) {
                System.out.println(String.format("Pedido #%d | Fecha: %td/%tm/%tY | Estado: %s | FormaPago: %s",
                        ped.getId(),
                        ped.getFecha(),
                        ped.getFecha(),
                        ped.getFecha(),
                        ped.getEstado(),
                        ped.getFormaPago() == null ? "N/A" : ped.getFormaPago()));
                System.out.println(" - Usuario: " + ped.getUsuario());
                System.out.println("----------------------------------------------");
                for (DetallePedido d : ped.getDetalles()) {
                    System.out.println(" - " + d);
                }
                System.out.println("Total Pedido: $" + ped.getTotal());
                System.out.println("----------------------------------------------");

            }
            System.out.println("===================================================================================\n");
        } else {
            System.out.println("No hay pedidos disponibles.");
        }
    }
        
    private static void crearPedidos(Scanner sc, ServicePedido sPedido) {
        System.out.println("\n== Crear pedido ==");

        if (!sPedido.getServiceUsuario().listaUsuariosDisponibles().isEmpty()) { // Antes de comenzar se verifica si hay usuarios disponibles

            // inic variables utilizadas
            Long id;
            int cantidad;
            Usuario usuario = null;
            Producto producto = null;
            boolean respuesta = false;
            Pedido pedidoCreado = null;

            // Selección de usuario
            System.out.println("\nUsuarios:");
            imprimirListaUsuarios(sPedido.getServiceUsuario().listaUsuariosDisponibles());
            System.out.print("Ingrese el id del usuario que desea asignar el pedido: ");
            id = leerLong(sc);
            try {
                usuario = sPedido.getServiceUsuario().buscarUsuarioPorId(id);
                System.out.println("Usuario Seleccionado: " + usuario + "\n");
            } catch (EntidadNoEncontradaException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            if (usuario != null) { // si es null es porque no se encontró, por lo tanto no se prosigue con la operación

                if (!sPedido.getServiceProducto().listaProductosDisponibles().isEmpty()) { // Antes de continuar verifica si hay productos disponibles

                    System.out.println("Productos:");
                    imprimirListaProductos(sPedido.getServiceProducto().listaProductosDisponibles());
                    System.out.print("Ingrese el id del producto que desea asignar el pedido: ");
                    id = leerLong(sc);
                    try {
                        producto = sPedido.getServiceProducto().buscarProductoPorId(id);
                        System.out.println("Producto Seleccionado: " + producto + "\n");
                    } catch (ProductoInexistenteException ex) {
                        System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
                    }

                    if (producto != null) { // si se encuentra el producto se pregunta la cantidad
                        System.out.println("\nIngrese la cantidad de unidades de " + producto.getNombre() + " | Stock actual: " + producto.getStock());
                        System.out.print("Seleccion: ");
                        try {
                            cantidad = leerInt(sc);
                            sPedido.getServiceProducto().verificarStock(cantidad, producto);
                            pedidoCreado = sPedido.crearPedido(usuario, producto, cantidad); // aquí se cuenta con todo lo necesario para crear el pedido
                            System.out.println("Pedido creado con id: #" + pedidoCreado.getId());
                        } catch (StockInvalidoException sie) {
                            System.out.println(sie.getClass().getSimpleName() + ": " + sie.getMessage());
                        } catch (NumberFormatException nfe) {
                            System.out.println(nfe.getClass().getSimpleName() + ": El valór ingresado no válido (Se espera un número)");
                        }
                    }
                    
                    if (pedidoCreado != null) {  // si el pedido se creó, se procede
                        do {                     // Se preguntará hasta que el usuario indique que no
                           try {                 // se da a elegír al usuario si quiere agregar mas detalles al mismo pedido
                            System.out.print("\nDesea añadir otro detalle al pedido (S/N): ");
                            respuesta = validarRespuesta(sc.nextLine());
                            if (respuesta) {     // si se confirma se inicia la creación
                                agregarDetalle(sc, pedidoCreado, sPedido);
                            }
                        }   catch (RespuestaInvalidaException ex) { 
                                System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
                                respuesta = false;
                            } 
                        } while (respuesta);
                        
                    }
                    
                } else {
                    System.out.println("No hay productos disponibles para crear un detalle");
                }

            }
        } else {
            System.out.println("No hay usarios disponibles para asignar al pedido.");
        }

    }
    
    public static void editarPedido(Scanner sc, ServicePedido sPedido)  {
        System.out.println("\n== Editar Pedido ==");
        
        if (!sPedido.listaPedidosDisponibles().isEmpty()) { // Se verifica que haya pedidos antes de continuar
            // inic variables utilizadas
            Long id;
            int opcion;
            Pedido pedido = null;
            
            System.out.println("\n== Seleccion del Pedido ==");
            imprimirListaPedidos(sPedido.listaPedidosDisponibles());
            System.out.print("Ingrese el id del pedido que desee editar: ");
            id = leerLong(sc);
            try {
                sPedido.buscarPedidoDispPorId(id);
                pedido = sPedido.buscarPedidoDispPorId(id);
                System.out.println("Pedido seleccionado: " + pedido);
                pedido.verificarEstado();
            } catch (EntidadNoEncontradaException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            } catch (PedidoNoEditableException pnee) {
                System.out.println(pnee.getClass().getSimpleName() + ": " + pnee.getMessage());
                pedido = null; // si el estado actual del pedido no permite editarlo, se asigna null para cancelar el menú de edición
            }
            
            
            if (pedido != null) { // si se encuentra el pedido y es editable, seguimos con la edición
                System.out.println("\nQue Desea Editar");
                System.out.println(" 1 - Estado");
                System.out.println(" 2 - Forma de Pago");
                System.out.println(" 3 - Ambos");
                System.out.println(" 4 - Agregar Detalle");
                System.out.print("Seleccion: ");
                opcion = validarOpcion(sc);
                
                switch (opcion) {
                    case 1:
                        editarEstado(sc, pedido);
                        break;
                        
                    case 2:
                        editarFormaPago(sc, pedido);
                        break;
                        
                    case 3:
                        editarEstado(sc, pedido);
                        editarFormaPago(sc, pedido);
                        break;
                        
                    case 4:
                        agregarDetalle(sc, pedido, sPedido);
                        break;
                        
                    default:
                        System.out.println("Se debe ingresár un valor de (1 - 3).");
                        break;
                }
                
                
                
                
            }
            
            
        } else {
            System.out.println("No hay pedidos disponibles.");
        }
    }
    
    public static void eliminarPedido(Scanner sc, ServicePedido sPedido) {
        if (!sPedido.listaPedidosDisponibles().isEmpty()) { // si la lista de pedidos disponibles no esta vacía se continúa

            // inic variables utilizadas
            Long id;
            int opcion;
            Pedido pedido = null;
            boolean confirmacion;

            System.out.println("\n== Seleccion del Pedido ==");
            imprimirListaPedidos(sPedido.listaPedidosDisponibles());
            System.out.print("Ingrese el id del pedido que desee eliminar: ");
            id = leerLong(sc);
            try {
                sPedido.buscarPedidoDispPorId(id);
                pedido = sPedido.buscarPedidoDispPorId(id);
                System.out.println("Pedido seleccionado: " + pedido);
            } catch (EntidadNoEncontradaException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            
            if (pedido != null) {
                System.out.print("Desea eliminar el pedído (S/N): ");
                confirmacion = validarRespuestaPrint(sc.nextLine());
                
                if (confirmacion) {
                    sPedido.eliminarPedido(pedido);
                }

            }
            
        } else {
            System.out.println("No hay pedidos disponibles.");
        }
    }
    
    // Métodos complementarios
    private static void editarEstado(Scanner sc, Pedido pedido) {
        
        System.out.println("Estado actual de pedido: " + pedido.getEstado());
        System.out.println("-- Seleccione el nuevo estado del pedido --");
        System.out.println(" 1 - Confirmado");
        System.out.println(" 2 - Terminado");
        System.out.println(" 3 - Cancelado");
        System.out.print("Seleccion: ");
        int opcion = validarOpcion(sc);
        
        switch (opcion) {
            case 1:
                pedido.setEstado(Estado.CONFIRMADO);
                System.out.println("Se modificó el estado.");
                break;
                
            case 2:
                pedido.setEstado(Estado.TERMINADO);
                System.out.println("Se modificó el estado.");
                break;
                
            case 3:
                pedido.setEstado(Estado.CANCELADO);
                pedido.reajustarStock();
                System.out.println("Se modificó el estado.");
                break;
                
            default:
                System.out.println("Se debe ingresár un número (1 - 3)");
                break;
        }
 
    }
    
    private static void editarFormaPago(Scanner sc, Pedido pedido) {
        
        int opcion;
        
        System.out.println("--Seleccione el Método de Pago--");
        System.out.println(" 1 - Tarjeta");
        System.out.println(" 2 - Transferencia");
        System.out.println(" 3 - Efectivo");
        System.out.print("Seleccion: ");
        opcion = validarOpcion(sc);
        
        switch (opcion) {
            case 1:
                pedido.setFormaPago(FormaPago.TARJETA);
                System.out.println("Método de pago modificado.");
                break;
                
            case 2:
                pedido.setFormaPago(FormaPago.TRANSFERENCIA);
                System.out.println("Método de pago modificado.");
                break;
                
            case 3:
                pedido.setFormaPago(FormaPago.EFECTIVO);
                System.out.println("Método de pago modificado.");
                break;
                
            default:
                System.out.println("Se debe ingresár un número (1 - 3)");
                break;
        }
    }
    
    public static void agregarDetalle(Scanner sc, Pedido pedido, ServicePedido sPedido) {
        System.out.println("\n== Agregar Detalle ==");
        if (!sPedido.getServiceUsuario().listaUsuariosDisponibles().isEmpty()) { // verifica que haya pedidos creados
            if (!sPedido.getServiceProducto().listaProductosDisponibles().isEmpty()) { // verifica si hay productos disponibles
                
                // inic variables utilizadas
                Long id;
                Producto producto = null;
                int cantidad;

                System.out.println("Productos: ");
                imprimirListaProductos(sPedido.getServiceProducto().listaProductosDisponibles());
                System.out.print("Ingrese el id del producto que desea asignar el pedido: ");
                id = leerLong(sc);
                try {
                    producto = sPedido.getServiceProducto().buscarProductoPorId(id);
                    System.out.println("Producto Seleccionado: " + producto + "\n");
                } catch (ProductoInexistenteException ex) {
                    System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
                }

                if (producto != null) {
                    System.out.println("\nIngrese la cantidad de unidades de " + producto.getNombre() + " | Stock actual: " + producto.getStock());
                    System.out.print("Seleccion: ");
                    try {
                        cantidad = leerInt(sc);
                        sPedido.getServiceProducto().verificarStock(cantidad, producto);
                        pedido.addDetallePedido(cantidad, producto);
                        System.out.println("Detalle añadido");
                    } catch (StockInvalidoException sie) {
                        System.out.println(sie.getClass().getSimpleName() + ": " + sie.getMessage());
                    } catch (NumberFormatException nfe) {
                        System.out.println(nfe.getClass().getSimpleName() + ": El valór ingresado inválido (Se espera un número)");
                    }
                }
            } else {
                System.out.println("No hay productos disponibles para crear un detalle");
            }
        } else {
            System.out.println("No hay pedidos disponibles para agregar detalle");
        }
    }
          
}
