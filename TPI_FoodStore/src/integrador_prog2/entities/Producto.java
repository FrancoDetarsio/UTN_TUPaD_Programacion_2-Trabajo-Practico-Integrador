package integrador_prog2.entities;

public class Producto extends Base {
    
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    
    
    public Producto(String nombre, double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        setDescripcion(descripcion);
        this.stock = stock;
        setImagen(imagen);
        this.disponible = disponible;
        this.categoria = categoria;
    }
    
    
    // Getters

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    
    // Setters

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.isBlank()) {
            this.descripcion = descripcion;
        } else {
            this.descripcion = "No Contiene";
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }

    public void setImagen(String imagen) {
        if (imagen != null && !imagen.isBlank()) {
            this.imagen = imagen;
        } else {
            this.imagen = "No Contiene";
        }
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
    
    
    @Override
    public String toString() {
        return String.format("Id: #%d | %s | $%.2f | Stock: %d | Categoría: %s", getId(), nombre, precio, stock, categoria.getNombre());
    }

}
