package integrador_prog2.UI;

import integrador_prog2.entities.Categoria;
import integrador_prog2.exception.CampoVacioException;
import integrador_prog2.exception.CategoriaExistenteException;
import integrador_prog2.exception.ProductoInexistenteException;
import integrador_prog2.exception.RelacionException;
import integrador_prog2.services.ServiceCategoria;
import java.util.Scanner;

public class MenuCategoria extends MenuBase {

    public static void iniciarMenu(Scanner sc, ServiceCategoria sCat) {
        mostrarSubMenu("Categorías");

        // variable para manejar menú con validación
        int opcion = validarOpcion(sc);

        switch (opcion) {
            case 1: // listar
                listarCategorias(sCat);
                break;

            case 2: // Crear
                crearCategoria(sc, sCat);
                break;

            case 3: // Editar
                editarCategoria(sc, sCat);
                break;

            case 4: // Eliminar
                eliminarCategoria(sc, sCat);
                break;

            default:
                System.out.println("Debe Ingresar un número de (1 - 4)\nIntente Nuevamente");
        }
    }

    
    // Métodos para cada opción de submenú
    
    private static void listarCategorias(ServiceCategoria sCat) {
        System.out.println("\n== Lista de Categrías ==");
        imprimirListaCategorias(sCat.listaCategoriasDisponibles());
    }

    private static void crearCategoria(Scanner sc, ServiceCategoria sCat) {
        // Inic variables utilizadas
        String nombre;
        String descripcion;
        Categoria categoriaCreada = null;
        
        try {
            System.out.println("\n== Crear Categoría ==");
            System.out.print("Ingrese el nombre de la Categoría: ");
            nombre = sc.nextLine();
            sCat.validarNombre(nombre);
            
            System.out.print("Ingrese una decripción: ");
            descripcion = sc.nextLine();
            sCat.validarDescripcion(descripcion);
            
            categoriaCreada = sCat.addCategoría(nombre, descripcion);
            
        } catch (CampoVacioException cve) {
            System.out.println(cve.getClass().getSimpleName() + ": " + cve.getMessage());
        } catch (CategoriaExistenteException cee) {
            System.out.println(cee.getClass().getSimpleName() + ": " + cee.getMessage());
        }
        
        if (categoriaCreada != null) {
            System.out.println("Categoría creada con el id: #" + categoriaCreada.getId());
        }
        
    }
    
    private static void editarCategoria(Scanner sc, ServiceCategoria sCat) {
        System.out.println("\n== Editar Categoría ==");        

        if (!sCat.listaCategoriasDisponibles().isEmpty()) { // se verifica si hay categorías disponibles
            // Inic variables utilizadas
            int opcion = 0;
            String nombre;
            String descripcion;
            Long id;
            Categoria categoriaEncontrada = null;

            imprimirListaCategorias(sCat.listaCategoriasDisponibles());
            System.out.print("Ingrese el ID de la Categoría que desee modificar: ");

            try {
                id = leerLong(sc);
                categoriaEncontrada = sCat.buscarCatPorId(id);
                System.out.println("\nCategoría Seleccionada:" + categoriaEncontrada);
            } catch (ProductoInexistenteException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }

            if (categoriaEncontrada != null) {

                System.out.println("\nQue operación quiere realizar?");
                System.out.println(" 1. Editar nombre");
                System.out.println(" 2. Editar Descripción");
                System.out.println(" 3. Editar Ambos");
                System.out.print("Seleccion: ");
                opcion = validarOpcion(sc);

                switch (opcion) {
                    case 1: // Editar Nombre
                        try {
                            System.out.print("\nIngrese el nuevo nombre: ");
                            nombre = sc.nextLine();
                            sCat.editarNombre(categoriaEncontrada, nombre);
                        } catch (CampoVacioException | CategoriaExistenteException e) {
                            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                        break;

                    case 2: // Editar Descripción
                        try {
                            System.out.print("\nIngrese la nueva descripción: ");
                            descripcion = sc.nextLine();
                            sCat.editarDescripcion(categoriaEncontrada, descripcion);
                        } catch (CampoVacioException e) {
                            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                        break;

                    case 3: // Editar Nombre y Descripción
                        try {
                            System.out.print("\nIngrese el nuevo nombre: ");
                            nombre = sc.nextLine();
                            sCat.editarNombre(categoriaEncontrada, nombre);

                            System.out.print("\nIngrese la nueva descripción: ");
                            descripcion = sc.nextLine();

                            sCat.editarNombreYDesc(categoriaEncontrada, descripcion, nombre);
                        } catch (CampoVacioException | CategoriaExistenteException e) {
                            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Debe Ingresar un número de (1 - 3)\nIntente Nuevamente");
                        break;
                }
            }
        } else {
            System.out.println("No hay categorías disponibles.");
        }
    }
    
    private static void eliminarCategoria(Scanner sc, ServiceCategoria sCat) {
        System.out.println("\n== Eliminar Categoría ==");

        if (!sCat.listaCategoriasDisponibles().isEmpty()) { // se verifica si hay categorías disponibles
            // Inic variables utilizadas
            Long id;
            Categoria categoriaEncontrada = null;
            boolean respuesta;

            imprimirListaCategorias(sCat.listaCategoriasDisponibles());
            System.out.print("Ingrese el id de la categoría que desee eliminar: ");

            try {
                id = leerLong(sc);
                categoriaEncontrada = sCat.buscarCatPorId(id);
                System.out.println("\nCategoría Seleccionada:" + categoriaEncontrada);
            } catch (ProductoInexistenteException ex) {
                System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }

            if (categoriaEncontrada != null) {
                try {
                    sCat.verificarProductosRelacionados(categoriaEncontrada); // se verifica si tiene productos asociados
                    System.out.print("Desea eliminar esta categoría? (S/N): ");
                    respuesta = validarRespuestaPrint(sc.nextLine());
                    sCat.eliminarCategoria(categoriaEncontrada, respuesta);
                } catch (RelacionException ex) {
                    System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
                }

            }
        } else {
            System.out.println("No hay categorías disponibles.");
        }
    }

}
