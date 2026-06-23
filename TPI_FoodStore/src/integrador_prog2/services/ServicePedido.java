package integrador_prog2.services;

import integrador_prog2.entities.Pedido;
import integrador_prog2.entities.Producto;
import integrador_prog2.entities.Usuario;
import integrador_prog2.enums.Estado;
import integrador_prog2.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

public class ServicePedido {
  
    private List<Pedido> pedidos;
    private ServiceUsuario serviceUsuario;
    private ServiceProducto serviceProducto;

    public ServicePedido(ServiceUsuario serviceUsuario, ServiceProducto serviceProducto) {
        this.serviceUsuario = serviceUsuario;
        this.serviceProducto = serviceProducto;
        this.pedidos = new ArrayList<>();
    }
    
    // Getters

    public ServiceUsuario getServiceUsuario() {
        return serviceUsuario;
    }

    public ServiceProducto getServiceProducto() {
        return serviceProducto;
    }
    
    
    
    /**
     * Creación de pedido, los valores se validaron previamente, se crea pedido
     * se crea detalle a travéz de método de pedido, se le asigna al usuario
     * se retorna el pedido creado
     * @param usuario
     * @param producto
     * @param cantidad 
     */
    public Pedido crearPedido(Usuario usuario, Producto producto, int cantidad) {
        Pedido nuevoPedido = new Pedido(usuario);                              // se crea pedido
        nuevoPedido.addDetallePedido(cantidad, producto);                      // se crea el detalle
        usuario.agregarPedido(nuevoPedido);                                    // se agrega el pedido al usuario
        pedidos.add(nuevoPedido);                                              // se agrega el pedido al servicio
        return nuevoPedido;                                                    // se retorna el pedido creado
    }
    
    /**
     * recorre la lista de pedidos y agrega a una nueva lista los pedidos que esten
     * disponibles. 
     * Si no se encontraron pedidos disponibles, se devuelve una lista vacía
     * 
     * @return List<Pedido>
     */
    public List<Pedido> listaPedidosDisponibles() {
        List<Pedido> listaPedidosDisponibles = new ArrayList<>();
        if (!pedidos.isEmpty()) {
            for (Pedido p : pedidos) {
                if (!p.isEliminado()) {
                    listaPedidosDisponibles.add(p);
                }
            }
        }
        return listaPedidosDisponibles;
    }
    
    /**
     * Recibe un id como parametro, realiza una busqueda en la lista de pedidos 
     * disponibles, si un pedido coincide con el id, se retorna el pedido
     * si no se encuentra el pedido se lanza ecepcion.
     * @param id
     * @return Pedido
     * @throws EntidadNoEncontradaException 
     */
    public Pedido buscarPedidoDispPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : listaPedidosDisponibles()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("El id ingresado no corresponde con un pedido disponible.");
    }
    
    /**
     * Se elimina el pedido eliminado = true
     * se reajusta el stock en caso de que corresponda con el estado del pedido
     * @param p 
     */
    public void eliminarPedido(Pedido p) {
        p.setEliminado(true);
        if (p.getEstado() == Estado.CONFIRMADO || p.getEstado() == Estado.PENDIENTE) {
            p.reajustarStock(); // se reajusta el stock si el pedido quedó en pendiente o confirmado a la hora de eliminarse
        }
    }
    
}
