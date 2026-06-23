package integrador_prog2.UI;

import integrador_prog2.entities.Categoria;
import integrador_prog2.entities.Producto;
import integrador_prog2.exception.CampoVacioException;
import integrador_prog2.exception.EntidadNoEncontradaException;
import integrador_prog2.exception.PrecioNegativoException;
import integrador_prog2.exception.ProductoExistenteException;
import integrador_prog2.exception.ProductoInexistenteException;
import integrador_prog2.exception.RespuestaInvalidaException;
import integrador_prog2.exception.StockInvalidoException;
import integrador_prog2.services.ServiceProducto;
import java.util.Scanner;

public class MenuProducto extends MenuBase {

    public static void iniciarMenu(Scanner sc, ServiceProducto sProd) {

        mostrarSubMenu("Productos");

        // variable para manejar menú con validación
        int opcion = validarOpcion(sc);

        switch (opcion) {
            case 1: // Listar
                listarProductos(sProd);
                break;

            case 2: // Crear
                crearProducto(sc, sProd);
                break;

            case 3: // Editar
                editarProducto(sc, sProd);
                break;

            case 4: // Eliminar
                eliminarProducto(sc, sProd);
                break;

            default:
                System.out.println("Debe Ingresar un número de (1 - 4)\nIntente Nuevamente");
        }
    }

    
    // Métodos para cada opción de submenú
    
    private static void listarProductos(ServiceProducto sProd) {
        System.out.println("\n== Listar Productos =="); // se muestran productos por categoría
        imprimirProductosXCategoria(sProd.getServiceCat().listaCategoriasDisponibles(), sProd.listaProductosDisponibles());
    }

    private static void crearProducto(Scanner sc, ServiceProducto sProd) {

        // Inic variables utilizadas
        String nombre;
        String descripcion;
        String imagen;
        int stock;
        double precio;
        boolean disponible;
        Long idCat;
        Categoria categoria;
        Producto productoCreado = null;
        

        System.out.println("\n== Crear Producto ==");
        // Antes de crear se verifica si hay categorías disponibles
        if (!sProd.getServiceCat().listaCategoriasDisponibles().isEmpty()) {
            try {
                
                System.out.print("Ingrese el nombre: ");
                nombre = sc.nextLine();
                sProd.validarNombre(nombre);
                
                System.out.print("Ingrese la descripción: ");
                descripcion = sc.nextLine();
                sProd.validarDescripcion(descripcion);
                
                System.out.print("Ingrese el Precio: $");
                precio = leerDouble(sc);
                sProd.validarPrecio(precio);
                
                System.out.print("Ingrese el Stock: ");
                stock = leerInt(sc);
                sProd.validarStock(stock);
                
                System.out.print("Ingrese URL de la imagen: ");
                imagen = sc.nextLine();
                sProd.validarImagen(imagen);
                
                System.out.print("Indique si el producto está disponible (S/N): ");
                disponible = validarRespuesta(sc.nextLine());
                
                System.out.println("\n== Lista Categorías ==");
                imprimirListaCategorias(sProd.getServiceCat().listaCategoriasDisponibles());
                System.out.print("\nIngrese el id de la categoría a la que pertenece: ");
                idCat = leerLong(sc);
                categoria = sProd.getServiceCat().buscarCatPorId(idCat);
                System.out.println("Categoría seleccionada: " + categoria);

                productoCreado = sProd.agregarNuevoProducto(nombre, descripcion, precio, stock, imagen, disponible, categoria);
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.getClass().getSimpleName() + ": Debe ingresar un número");
            } catch (ProductoExistenteException | CampoVacioException | PrecioNegativoException | StockInvalidoException | RespuestaInvalidaException | ProductoInexistenteException ex) {
                System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
            
            if (productoCreado != null) {
                System.out.println("\nProducto creado con id: #" + productoCreado.getId());
            }
            
        } else {
            System.out.println("No hay categorías\nSe debe crear una para poder agregar un nuevo producto.");
        }
    }

    private static void editarProducto(Scanner sc, ServiceProducto sProd) {

        // Inic variables utilizadas
        int opcion;
        int stock;
        double precio;
        Long id;
        Producto prodSeleccionado = null;
        Categoria catEncontrada;
        

        System.out.println("\n== Editar Producto ==");

        if (!sProd.listaProductosDisponibles().isEmpty()) {   // si hay productos disponibles se continúa
            System.out.println("productos disponibles: ");
            imprimirProductosXCategoria(sProd.getServiceCat().listaCategoriasDisponibles(), sProd.listaProductosDisponibles());

            try {
                System.out.print("Seleccione el id del producto a editar: ");
                id = leerLong(sc);
                prodSeleccionado = sProd.buscarProductoPorId(id);
                System.out.println("\nProducto Seleccionado: " + prodSeleccionado);
            } catch (ProductoInexistenteException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            if (prodSeleccionado != null) { // si se encontró, lanzamos el menú de edición

                // == Menú de edición ==
                System.out.println("\nQue operación desea realizar");
                System.out.println(" 1 - Editar Stock");
                System.out.println(" 2 - Editar Precio");
                System.out.println(" 3 - Editar Categoría");
                System.out.println(" 4 - Editar Todo (S/P/C)");
                System.out.print("Seleccion: ");
                opcion = validarOpcion(sc);
                

                switch (opcion) {
                    case 1: // Editar Stock

                        try {
                            System.out.print("\nIngrese el nuevo Stock: ");
                            stock = leerInt(sc);
                            sProd.editarStock(stock, prodSeleccionado);
                            System.out.println("Stock Actualizado.");
                        } catch (NumberFormatException nfe) {
                            System.out.println(nfe.getClass().getSimpleName() + ": El valor ingresado no corresponde a un número válido");
                        } catch (StockInvalidoException sie) {
                            System.out.println(sie.getClass().getSimpleName() + ": " + sie.getMessage());
                        }
                        break;

                    case 2: // Editar Precio

                        
                        try {
                            System.out.print("\nIngrese el nuevo Precio: $");
                            precio = leerDouble(sc);
                            sProd.editarPrecio(precio, prodSeleccionado);
                            System.out.println("Precio Actualizado.");
                        } catch (NumberFormatException nfe) {
                            System.out.println(nfe.getClass().getSimpleName() + ": El valor ingresado no corresponde a un número válido");
                        } catch (PrecioNegativoException pne) {
                            System.out.println(pne.getClass().getSimpleName() + ": " + pne.getMessage());
                        }
                        break;

                    case 3: // Editar Categoría

                        try {
                            System.out.println("\n== Categorías ==");
                            imprimirListaCategorias(sProd.getServiceCat().listaCategoriasDisponibles());
                            System.out.print("seleccione el ID de la categoría a la que desea agregar el producto: ");
                            id = leerLong(sc);
                            catEncontrada = sProd.getServiceCat().buscarCatPorId(id);
                            sProd.editarCategoria(catEncontrada, prodSeleccionado);
                            System.out.println("Categoría modificada.");
                        } catch (ProductoInexistenteException pie) {
                            System.out.println(pie.getClass().getSimpleName() + ": " + pie.getMessage());
                        }
                        break;

                    case 4: // Editar S/P/C
                        try {
                            System.out.print("\nIngrese el nuevo Stock: ");
                            stock = leerInt(sc);
                            sProd.validarStock(stock);
                            
                            System.out.print("\nIngrese el nuevo Precio: $");
                            precio = leerDouble(sc);
                            sProd.validarPrecio(precio);
                            
                            System.out.println("\n== Categorías ==");
                            imprimirListaCategorias(sProd.getServiceCat().listaCategoriasDisponibles());
                            System.out.print("seleccione el ID de la categoría a la que desea agregar el producto: ");
                            id = leerLong(sc);
                            catEncontrada = sProd.getServiceCat().buscarCatPorId(id);
                            
                            sProd.editarStockPrecioCategoria(stock, precio, catEncontrada, prodSeleccionado);
                            System.out.println("Los datos se modificaron exitosamente.");
                        } catch (NumberFormatException nfe) {
                            System.out.println(nfe.getClass().getSimpleName() + ": El valor ingresado no corresponde a un número válido");
                        } catch (StockInvalidoException | PrecioNegativoException | EntidadNoEncontradaException | ProductoInexistenteException ex) {
                            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
                        }
                        break;
                        
                    default:
                        System.out.println("Debe ingresár un valór de (1 - 4)\nIntente nuevamente");
                }

            } else {
                System.out.println("No se encontraron productos disponibles.");
            }
        } else {
            System.out.println("No hay productos disponibles.");
        }
    }

    private static void eliminarProducto(Scanner sc, ServiceProducto sProd) {
        
        System.out.println("\n== Eliminar Producto ==");
        
        if (!sProd.listaProductosDisponibles().isEmpty()) {
            // inic variables utilizadas
            Producto prodSeleccionado = null;
            boolean respuesta;
            Long id;
            
            System.out.println("productos disponibles: ");
            imprimirProductosXCategoria(sProd.getServiceCat().listaCategoriasDisponibles(), sProd.listaProductosDisponibles());
            System.out.print("\nSeleccione el id del producto a eliminar: ");
            
            try {
                id = leerLong(sc);
                prodSeleccionado = sProd.buscarProductoPorId(id);
                System.out.println("\nProducto Seleccionado: " + prodSeleccionado); // producto seleccionado que se eliminará
            } catch (ProductoInexistenteException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            if (prodSeleccionado != null) {
                System.out.println("Realmente desea eliminar el producto: " + prodSeleccionado.getNombre());
                System.out.print("Seleccion (S/N): ");
                
                respuesta = validarRespuestaPrint(sc.nextLine());
                sProd.eliminarProducto(prodSeleccionado, respuesta);

            } else {
                System.out.println("No se encontraron productos disponibles.");
            }
        } else {
            System.out.println("No hay productos disponibles.");
        }
    }

}
