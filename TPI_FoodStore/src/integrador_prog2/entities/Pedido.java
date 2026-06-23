package integrador_prog2.entities;

import integrador_prog2.enums.Estado;
import integrador_prog2.enums.FormaPago;
import integrador_prog2.exception.PedidoNoEditableException;
import integrador_prog2.interfaces.Calculable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable{
    
    private LocalDateTime fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;

    
    public Pedido(Usuario usuario) {
        super();
        this.fecha = LocalDateTime.now();
        this.estado = estado.PENDIENTE; // por defecto inician en pendiente
        this.total = 0;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
    }
    
    
    // Getters

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    // Setters

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario != null && this.usuario != usuario) {
            this.usuario = usuario;
            usuario.agregarPedido(this);
        }
    }
    
    
    

    // Métodos de clase

    @Override // Interfáz Calculable
    /**
     * por cada detalle modificado, se recalcula el total en base a sus subtotales
     */
    public void calcularTotal() {
        total = 0;
        for (DetallePedido detalle : detalles) {
            total += detalle.getSubtotal();
        }
    }
    
    /**
     * Crea un detalle de pedido
     * se recalcula el total del pedido con calcularToral()
     * se actualiza el stock del producto restando la cantidad del detalle
     * @param cantidad
     * @param p 
     */
    public void addDetallePedido(int cantidad, Producto p) {
        detalles.add(new DetallePedido(cantidad, p));
        calcularTotal();
        p.setStock(p.getStock() - cantidad);
    }
    
    /**
     * Recibe un producto como parámetro, se busca un detalle en la lista de 
     * detalles que coincida con el producto
     * si no se encuentra se devuelve null
     * @param P
     * @return DetallePedido
     */
    public DetallePedido findDetallePedidoByProducto(Producto P) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(P)) {
                return detalle;
            }
        }
        return null;
    }
    
    /**
     * recibe un producto como parámetro
     * verifica si se puede eliminar el detalle del pedido (dependiendo su estado)
     * si el detalle se encuentra, se elimina de la lista de detalles del pedido
     * retorna el detalle eliminado o null si no se encontró
     * @param P
     * @return DetallePedido
     * @throws PedidoNoEditableException 
     */
    public DetallePedido deleteDetallePedidoByProducto(Producto P) throws PedidoNoEditableException {
        verificarEstado();
        DetallePedido detalle = findDetallePedidoByProducto(P);
        if (detalle != null) {
            detalles.remove(detalle);
        }
        return detalle;
    }
    
    /**
     * verifica si el estado es terminado o pendiente para asegurarse que no se modifique
     * si cumpe con alguno de estos estados se lanza excepcion
     * @throws PedidoNoEditableException 
     */
    public void verificarEstado() throws PedidoNoEditableException{
        if (this.estado == estado.TERMINADO) {
            throw new PedidoNoEditableException("El pedido no puede editarse, está en estado Terminado.");
        }
        
        if (this.estado == estado.CANCELADO) {
            throw new PedidoNoEditableException("El pedido no puede editarse, está en estado Cancelado.");
        }
    }
    
    /**
     * al cancelar un pedido, los detalles de este devuelven las cantidades
     * asignadas de productos al stock del producto
     */
    public void reajustarStock() {
        int cantidad;
        Producto producto;
        for (DetallePedido detalle : detalles) {
            cantidad = detalle.getCantidad();
            producto = detalle.getProducto();
            producto.setStock(producto.getStock() + cantidad);
        }
    }
    
    
    @Override
    public String toString() {
        return String.format("Pedido #%d\n - Usuario: %s \n - Forma de Pago: %s | Estado: %s | %td/%tm/%tY\nTotal: $%.2f\n",
                getId(), usuario, 
                formaPago == null ? "N/A" : formaPago,
                estado, fecha, fecha, fecha, total);
    }

}
