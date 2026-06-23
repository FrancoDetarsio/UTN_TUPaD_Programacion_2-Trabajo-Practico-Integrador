package integrador_prog2.services;

import integrador_prog2.entities.Categoria;
import integrador_prog2.entities.Producto;
import integrador_prog2.exception.CampoVacioException;
import integrador_prog2.exception.EntidadNoEncontradaException;
import integrador_prog2.exception.PrecioNegativoException;
import integrador_prog2.exception.ProductoExistenteException;
import integrador_prog2.exception.ProductoInexistenteException;
import integrador_prog2.exception.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProducto {

    private List<Producto> productos;
    private ServiceCategoria serviceCat;
    
    
    public ServiceProducto(ServiceCategoria sCat) {
        super();
        this.productos = new ArrayList<>();
        this.serviceCat = sCat;
    }
    
    
    // Getters

    public ServiceCategoria getServiceCat() {
        return serviceCat;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    
    
    /**
     * toma los parámetros validados para crear un nuevo producto
     * el producto creado se agrega a la lista de productos, y se agrega a la
     * lista de productos de la categoría.
     * @param nombre
     * @param descripcion
     * @param precio
     * @param stock
     * @param imagen
     * @param disponible
     * @param categoria
     * @return Producto
     */
    public Producto agregarNuevoProducto(String nombre, String descripcion,
            double precio, int stock, String imagen, boolean disponible, Categoria categoria) {
        Producto p = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
        productos.add(p);                      // se agrega a lista de productos del servicio
        categoria.getProductos().add(p);       // se agrega a lista de productos de su categoría
        return p;                              // se devuelve producto creado
    }
    
    /**
     * Recibe un id como parámetro y busca un producto con este id en la lista de
     * productos disponibles
     * @param id
     * @return Producto
     * @throws ProductoInexistenteException 
     */
    public Producto buscarProductoPorId(Long id) throws ProductoInexistenteException {
        if (id != null) {
            for (Producto p : listaProductosDisponibles()) {
                if (p.getId().equals(id)) {
                    return p;
                }
            }
        }
        throw new ProductoInexistenteException("No existe un producto para el id ingresado");
    }
    
    /**
     * Recibe Producto y boolean como respuesta del usuario, si la respuesta fue
     * true se establece la categoría como eliminada
     * @param p
     * @param respuesta
     */
    public void eliminarProducto(Producto p, boolean respuesta) {
        if (respuesta) {
            p.setEliminado(true);
        }
    }
    
    
    /**
     * Crea una lista de Productos que esten disponibles
     * (Eliminado = false), si no encuentra productos devuelve null
     * @return  List<Productos>
     */
    public List<Producto> listaProductosDisponibles() {
        List<Producto> listaProductosDisponibles = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                listaProductosDisponibles.add(p);
            }
        }
        return listaProductosDisponibles;
    }
    
    
    // Métodos de vlidación
    
    /**
     * Verifica que el campo no quede vacio y que el producto no este repetido
     * en la lista de productos
     * @param nombre
     * @throws CampoVacioException
     * @throws ProductoExistenteException
     */
    public void validarNombre(String nombre) throws CampoVacioException, ProductoExistenteException {
        if (nombre == null || nombre.isBlank()) {
            throw new CampoVacioException("El nombre no puede quedar vacío");
        }
        verificarDuplicado(nombre);
    }
    
    /**
     * verifica que la cantidad solicitada de unidades del producto no supere a 
     * la cantidad disponible. 
     * si se supera, se lanza excepcion
     * @param cantidad
     * @param p
     * @throws StockInvalidoException
     */
    public void verificarStock(int cantidad, Producto p) throws StockInvalidoException {
        if ((p.getStock() - cantidad) < 0) {
            throw new StockInvalidoException("La cantidad ingresada es mayor al stock disponible.");
        }
    }
    
    /**
     * Verifica que el precio ingresado no sea negativo, si lo es se lanza
     * excepcion.
     * @param precio
     * @throws PrecioNegativoException
     */
    public void validarPrecio(double precio) throws PrecioNegativoException {
        if (precio < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo");
        }
    }
    
    /**
     * Verifica que el stock ingresado no sea negativo, si lo es se lanza
     * excepcion.
     * @param stock
     * @throws StockInvalidoException
     */
    public void validarStock(int stock) throws StockInvalidoException {
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo");
        }
    }
    
    /**
     * Verifica que la descripcion no quede vacía
     * @param descripcion
     * @throws CampoVacioException
     */
    public void validarDescripcion(String descripcion) throws CampoVacioException {
        if (descripcion == null || descripcion.isBlank()) {
            throw new CampoVacioException("La descripción no puede quedar vacía.");
        }
    }

    /**
     * Verifica que la URL de imagen no quede vacía
     * @param imagen
     * @throws CampoVacioException
     */
    public void validarImagen(String imagen) throws CampoVacioException {
        if (imagen == null || imagen.isBlank()) {
            throw new CampoVacioException("La URL de imagen no puede quedar vacía.");
        }
    }

    /**
     * Verifica si el producto existe en la lista de productos disponibles, si
     * existe lanza una excepcion.
     * @param nombre
     * @throws ProductoExistenteException
     */
    private void verificarDuplicado(String nombre) throws ProductoExistenteException {
        if (!listaProductosDisponibles().isEmpty()) {
            for (Producto p : listaProductosDisponibles()) {
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    throw new ProductoExistenteException("El producto ingresado ya existe.");
                }
            }
        }
    }
    
    
    
    // Métodos de edición
    
    /**
     * Recibe categoria y producto por parámetro
     * se elimina el producto de la categoria anterior
     * se asigna la categoria recibida al producto
     * se agrega el producto a su nueva categoría
     * @param cat
     * @param p 
     */
    public void editarCategoria(Categoria cat, Producto p) {
        p.getCategoria().eliminarProductoDeCategoria(p);
        p.setCategoria(cat);
        p.getCategoria().agregarProductoACategoria(p);
    }
    
    /**
     * Recibe un stock y un producto como parámetros
     * se valida el stock y se asigna al producto
     * @param Stock
     * @param p
     * @throws StockInvalidoException 
     */
    public void editarStock(int Stock, Producto p) throws StockInvalidoException {
        validarStock(Stock);
        p.setStock(Stock);
    }
    
    /**
     * Recibe un precio y un producto como parámetros
     * se valida el precio y se asigna al producto
     * @param precio
     * @param p
     * @throws PrecioNegativoException 
     */
    public void editarPrecio(double precio, Producto p) throws PrecioNegativoException {
        validarPrecio(precio);
        p.setPrecio(precio);
    }
    
    /**
     * Permite modificar 3 atributos en un solo método, precio y stock previamente
     * validados
     * @param stock
     * @param precio
     * @param catEncontrada
     * @param prodSeleccionado
     * @throws EntidadNoEncontradaException 
     */
    public void editarStockPrecioCategoria(int stock, double precio, Categoria catEncontrada, Producto prodSeleccionado) throws EntidadNoEncontradaException {
        prodSeleccionado.setStock(stock);
        prodSeleccionado.setPrecio(precio);
        editarCategoria(catEncontrada, prodSeleccionado);
    }
    
}
