package integrador_prog2.UI;

import integrador_prog2.entities.Usuario;
import integrador_prog2.exception.EmailInvalidoException;
import integrador_prog2.exception.EntidadNoEncontradaException;
import integrador_prog2.exception.UsuarioInvalidoException;
import integrador_prog2.services.ServiceUsuario;
import java.util.Scanner;


public class MenuUsuario extends MenuBase {

    public static void iniciarMenu(Scanner sc, ServiceUsuario sUsuario) {
        mostrarSubMenu("Usuarios");

        // variable para manejar menú con validación
        int opcion = validarOpcion(sc);

        switch (opcion) {
            case 1: // Listar
                listarUsuarios(sUsuario);
                break;

            case 2: // Crear
                crearUsuario(sc, sUsuario);
                break;

            case 3: // Editar
                editarUsuario(sc, sUsuario);
                break;

            case 4: // Eliminar
                eliminarUsuario(sc, sUsuario);
                break;

            default:
                System.out.println("Debe Ingresar un número de (1 - 4)\nIntente Nuevamente");
        }
    }

    
    // Métodos para cada opción de submenú
    
    private static void listarUsuarios(ServiceUsuario sUsuario) {
        System.out.println("\n== Listar Usuarios ==");
        imprimirListaUsuarios(sUsuario.listaUsuariosDisponibles());
    }
    
    private static void crearUsuario(Scanner sc, ServiceUsuario sUsuario) {
        // Inic variables utilizadas
        String nombre;
        String apellido;
        String celular;
        String email;
        boolean bandera;
        Usuario usuarioCrado;

        System.out.println("\n== Crear usuario ==");

        try {
            System.out.print("Ingrese el nombre: ");
            nombre = sc.nextLine();
            sUsuario.validarNombre(nombre);

            System.out.print("Ingrese el apellido: ");
            apellido = sc.nextLine();
            sUsuario.validarApellido(apellido);

            System.out.print("Ingrese el número de celular: ");
            celular = sc.nextLine();
            sUsuario.validarCelular(celular);

            System.out.print("Ingrese el email: ");
            do {
                bandera = false;
                email = sc.nextLine();
                try {
                    sUsuario.validarEmail(email);
                } catch (EmailInvalidoException eie) {
                    System.out.println(eie.getClass().getSimpleName() + ": " + eie.getMessage());
                    System.out.print("Ingrese un email nuevo: ");
                    bandera = true;
                }
            } while (bandera);

            usuarioCrado = sUsuario.crearUsuario(nombre, apellido, celular, email);
            System.out.println("Usuario creado con id: #" + usuarioCrado.getId());
        } catch (UsuarioInvalidoException ex) {
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
        
    }
    
    private static void editarUsuario(Scanner sc, ServiceUsuario sUsuario) {
        if (!sUsuario.listaUsuariosDisponibles().isEmpty()) { // validación inicial para ver si hay usuarios disponibles para editar
            // inic variables utilizadas
            Long id;
            int opcion;
            String nombre;
            String apellido;
            String celular;
            String email;
            Usuario usuario = null;
            
            System.out.println("\n== Editar Usuario ==");
            
            imprimirListaUsuarios(sUsuario.listaUsuariosDisponibles());
            System.out.print("Seleccione el id del usuario a editar: ");
            id = leerLong(sc);
            
            try {
                usuario = sUsuario.buscarUsuarioPorId(id);
                System.out.println("Usuario Seleccionado: " + usuario);
            } catch (EntidadNoEncontradaException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            
            if (usuario != null) { // si es null es porque no se encontró, por lo tanto no se pregunta que editar

                System.out.println("\nQue desea editar: ");
                System.out.println(" 1 - Nombre");
                System.out.println(" 2 - Apellido");
                System.out.println(" 3 - Celular");
                System.out.println(" 4 - email");
                System.out.println(" 5 - Rol");
                System.out.print("Seleccion: ");

                opcion = validarOpcion(sc);

                switch (opcion) {
                    case 1:
                        
                        try { // Editar Nombre
                            System.out.print("\nIngrese el nuevo Nombre: ");
                            nombre = sc.nextLine();
                            sUsuario.modificarNombre(nombre, usuario);
                            System.out.println("Nombre de usuario modificado exitosamente.");
                        } catch (UsuarioInvalidoException e) {
                            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                        break;

                    case 2:
                        
                        try { // Editar Apellido
                            System.out.print("\nIngrese el nuevo Apellido: ");
                            apellido = sc.nextLine();
                            sUsuario.modificarApellido(apellido, usuario);
                            System.out.println("Apellido de usuario modificado exitosamente.");
                        } catch (UsuarioInvalidoException e) {
                            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                        break;

                    case 3:
                        
                        try { // Editar Celular
                            System.out.print("\nIngrese el nuevo Celular: ");
                            celular = sc.nextLine();
                            sUsuario.modificarCelular(celular, usuario);
                            System.out.println("Celular de usuario modificado exitosamente.");
                        } catch (UsuarioInvalidoException e) {
                            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
                        }
                        break;

                    case 4:
                        
                        try { // Editar email
                            System.out.print("\nIngrese el nuevo email: ");
                            email = sc.nextLine();
                            sUsuario.modificarEmail(email, usuario);
                            System.out.println("email modificado exitosamente.");
                        } catch (EmailInvalidoException e) {
                           System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage()); 
                        }
                        break;

                    case 5: // Editar Rol
                        sUsuario.modificarRol(usuario);
                        System.out.println("Se modificó el Rol del Usuario.");
                        System.out.println("El Rol actual del es: \"" + usuario.getRol() + "\"");
                        break;

                    default:
                        System.out.println("Se debe ingresar un número (1 - 5)");
                        break;
                }

            }

        } else {
            System.out.println("No hay usuarios disponibles para editar.");
        }
    }

    private static void eliminarUsuario(Scanner sc, ServiceUsuario sUsuario) {
        System.out.println("\n== Eliminar Usuario ==");
        
        if (!sUsuario.listaUsuariosDisponibles().isEmpty()) {
            // inic variables utilizadas
            Long id;
            Usuario usuario;
            String respuesta;
            
            System.out.println("Ingrese el id del usuario que desea eliminar: ");
            imprimirListaUsuarios(sUsuario.listaUsuariosDisponibles());
            System.out.print("Seleccion: ");
            id = leerLong(sc);

            try {
                usuario = sUsuario.buscarUsuarioPorId(id);
                System.out.println("usuario seleccionado: " + usuario);
                System.out.print("Desea eliminarlo (S/N): ");
                respuesta = sc.nextLine();
                usuario.setEliminado(validarRespuestaPrint(respuesta));
            } catch (EntidadNoEncontradaException e) {
                System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            }
            
        } else {
            System.out.println("No hay usuarios disponibles.");
        }
        
    }
    
}