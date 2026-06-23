package integrador_prog2.entities;

public class DetallePedido extends Base {
    
    private int cantidad;
    private double subtotal;
    private Pedido pedido;
    private Producto producto;
    

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal(cantidad, producto); // al momento de crearse se calcula el subtotal
    }

    
    // Getters
    
    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Producto getProducto() {
        return producto;
    }
    
    // Setters

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    
    /**
     * toma la cantidad ingresada de unidades y lo multiplica por el preicio
     * del producto, generando el subtotal
     * @param cantidad
     * @param producto
     * @return double subtotal
     */
    private double calcularSubtotal(int cantidad, Producto producto) {
        return cantidad * producto.getPrecio();
    }

    @Override
    public String toString() {
        return String.format("DetallePedido #%d: %s X %d => $%.2f", getId(), producto.getNombre(), cantidad, subtotal);
    }

}
