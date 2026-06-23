package integrador_prog2.entities;

import integrador_prog2.exception.CampoVacioException;
import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base {
    
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    
    public Categoria(String nombre, String descripcion) {
        super();
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        productos = new ArrayList<>();
    }

    
    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    // Setters

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.isBlank()) {
            this.descripcion = descripcion;
        } 
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    
    // Métodos de clase
    
    /**
     * Si el objeto existe en la lista de productos, este se elimina de la lista
     * @param producto 
     */
    public void eliminarProductoDeCategoria(Producto producto) {
        Producto prodAEliminar = null;
        for (Producto p : productos) {
            if (p.equals(producto)) {
                prodAEliminar = p;
            }
        }
        
        if (prodAEliminar != null) {
            productos.remove(prodAEliminar);
        }
    }
    
    /**
     * Añade producto a la lista de productos de esta categoría
     * @param producto 
     */
    public void agregarProductoACategoria(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }
    
    @Override
    public String toString() {
        return String.format(" - id: #%d | Nombre: %s | Descripción: %s", getId(), nombre, descripcion);
    }

}
