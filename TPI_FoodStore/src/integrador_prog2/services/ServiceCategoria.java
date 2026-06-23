package integrador_prog2.services;

import integrador_prog2.entities.Categoria;
import integrador_prog2.exception.CampoVacioException;
import integrador_prog2.exception.CategoriaExistenteException;
import integrador_prog2.exception.ProductoInexistenteException;
import integrador_prog2.exception.RelacionException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategoria {
    
    private List<Categoria> categorias;

    
    public ServiceCategoria() {
        super();
        this.categorias = new ArrayList<>();
    }

    
    /**
     * Devuelve una lista completa de categorías
     * @return 
     */
    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    /**
     * Crea una lista de categorías que esten disponibles, si no encuentra
     * categorías devuelve lista vacía
     * @return List<Categoria>
     */
    public List<Categoria> listaCategoriasDisponibles() {
        List<Categoria> listaDeCatDisponibles = new ArrayList<>();
        for (Categoria cat : categorias) {
            if (!cat.isEliminado()) {
                listaDeCatDisponibles.add(cat);
            }
        }
        return listaDeCatDisponibles;
    }

    /**
     * recibe parametros para crear una nueva categoría
     * crea la categoría y se añade a la lista de categorías
     * se devuelve la categoría creada
     * @param nombre
     * @param descripcion
     * @return Categoria
     */
    public Categoria addCategoría(String nombre, String descripcion) {
        Categoria nuevaCategoria = new Categoria(nombre, descripcion);
        categorias.add(nuevaCategoria);
        return nuevaCategoria;
    }
    
    /**
     * Recibe un id como parámetro, se verifica que no sea null
     * se busca si existe una categoría con el mismo id ingresado en 
     * la lista de categorias disponibles
     * Si se encuentra se devuelve la categoria
     * Si no se encuentra se lanza ProductoInexistenteException
     * @param id
     * @return Categoria
     * @throws integrador_prog2.exception.ProductoInexistenteException
     */
    public Categoria buscarCatPorId(Long id) throws ProductoInexistenteException {
        if (id != null) {
            for (Categoria cat : listaCategoriasDisponibles()) {
                if (cat.getId().equals(id)) {
                    return cat;
                }
            }
        }
        throw new ProductoInexistenteException("No existe una categoría con el id ingresado");
    }
    
    /**
     * Recibe categoría y boolean como respuesta del usuario si la respuesta fue
     * true se establece la categoría como eliminada
     * @param cat
     * @param respuesta
     */
    public void eliminarCategoria(Categoria cat, boolean respuesta) {
        if (respuesta) {
            cat.setEliminado(true);
        }
    }
    
    
    // Métodos de validación
    
    /**
     * Valida que el nombre de la categoría no quede vacío y que no se encuentre
     * ya creada en la lista de categorias disponibles si queda vacío lanza
     * CampoVacioException si no se encuentra lanza CategoriaExistenteException
     * @param nombre
     * @throws CampoVacioException
     * @throws CategoriaExistenteException
     */
    public void validarNombre(String nombre) throws CampoVacioException, CategoriaExistenteException {
        if (nombre == null || nombre.isBlank()) {
            throw new CampoVacioException("El Nombre no puede quedar vacío.");
        }
        validarNombreDuplicado(nombre);
    }

    /**
     * Se recibe un nombre de categoría por parámetro y se verifica si se
     * encuentra una categoriía con el mismo nombre el la lista de categorías
     * disponibles si se encuantra lanza CategoriaExistenteException
     * @param nombre
     * @throws CategoriaExistenteException
     */
    private void validarNombreDuplicado(String nombre) throws CategoriaExistenteException {
        for (Categoria cat : listaCategoriasDisponibles()) {
            if (cat.getNombre().equalsIgnoreCase(nombre)) {
                throw new CategoriaExistenteException("ya existe una categoría creada con el nombre ingresado.");
            }
        }
    }

    /**
     * Valida que la descripción de la categoría no quede vacía si queda vacía
     * lanza excepción
     * @param descripcion
     * @throws CampoVacioException
     */
    public void validarDescripcion(String descripcion) throws CampoVacioException {
        if (descripcion == null || descripcion.isBlank()) {
            throw new CampoVacioException("La descripción no puede quedar vacía.");
        }
    }
    
    /**
     * Recibe una categoría por parámetro y verifica si tiene productos relacionados
     * si los tiene se lanza excepcion
     * @param cat
     * @throws RelacionException 
     */
    public void verificarProductosRelacionados(Categoria cat) throws RelacionException {
        if (!cat.getProductos().isEmpty()) {
            throw new RelacionException("La categoría tiene productos asociados, no puede eliminarse.");
        }
    }
    
    
    // Métodos de edición
    
    /**
     * Se recibe una categoría y un nombre por parámetro, se valida y
     * se asigna a la categoría
     * @param cat
     * @param nombre
     * @throws CampoVacioException 
     * @throws CategoriaExistenteException 
     */
    public void editarNombre(Categoria cat, String nombre) throws CampoVacioException, CategoriaExistenteException {
        validarNombre(nombre);
        cat.setNombre(nombre);
    }
    
    /**
     * Se recibe una categoría y una descripción por parámetro, se valida y
     * se asigna a la categoría
     * @param cat
     * @param descripcion
     * @throws CampoVacioException 
     */
    public void editarDescripcion(Categoria cat, String descripcion) throws CampoVacioException {
        validarDescripcion(descripcion);
        cat.setDescripcion(descripcion);
    }
    
    /**
     * Se recibe una categoría, una descripción y un nombre por parámetro, se validan y
     * se asignan a la categoría
     * @param cat
     * @param nombre
     * @param descripcion
     * @throws CampoVacioException
     * @throws CategoriaExistenteException 
     */
    public void editarNombreYDesc(Categoria cat, String nombre, String descripcion) throws CampoVacioException, CategoriaExistenteException {
        editarNombre(cat, nombre);
        editarDescripcion(cat, descripcion);
    }
  
}
