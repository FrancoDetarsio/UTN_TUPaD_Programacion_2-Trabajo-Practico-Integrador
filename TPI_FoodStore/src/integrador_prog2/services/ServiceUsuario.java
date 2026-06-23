package integrador_prog2.services;

import integrador_prog2.entities.Usuario;
import integrador_prog2.enums.Rol;
import integrador_prog2.exception.EmailInvalidoException;
import integrador_prog2.exception.EntidadNoEncontradaException;
import integrador_prog2.exception.UsuarioInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUsuario {
    
    private List<Usuario> usuarios;

    public ServiceUsuario() {
        this.usuarios = new ArrayList<>();
    }

    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    /**
     * Crea una lista de usuarios solo con los que esten disponibles, si no
     * se encuentran se devuelve una lista vacía.
     * @return List<Usuario>
     */
    public List<Usuario> listaUsuariosDisponibles() {
        List<Usuario> listaUsuariosDisponibles = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                listaUsuariosDisponibles.add(u);
            }
        }
        return listaUsuariosDisponibles;
    }
    
    /**
     * Recibe parámetros necesarios para crear un usuario, se crea el usuario,
     * se asigna a la lista de usuarios y se retorna.
     * @param nombre
     * @param apellido
     * @param celular
     * @param email
     * @return
     * @throws UsuarioInvalidoException 
     */
    public Usuario crearUsuario(String nombre, String apellido, String celular, String email) throws UsuarioInvalidoException {
        Usuario u = new Usuario(nombre, apellido, email, celular);
        usuarios.add(u);
        return u;
    }
    
    /**
     * Recibe como parametro un id, se realiza una busqueda de la lista de 
     * usuarios disponibles, si el usuario con el id
     * correspondiente se encuentra, este se retorna, de lo contrario se lanza excepcion
     * @param id
     * @return Usuario
     * @throws EntidadNoEncontradaException 
     */
    public Usuario buscarUsuarioPorId(Long id) throws EntidadNoEncontradaException {
        if (id != null) {
            for (Usuario u : listaUsuariosDisponibles()) {
                if (u.getId().equals(id)) {
                    return u;
                }
            }
        }
            throw new EntidadNoEncontradaException("El id ingresado no corresponde a un usuario disponible.");
    }
    
   
    // Modificaciónes
    
    /**
     * Recibe nombre y Usuario como parámetro, se valida el nombre y se actualiza
     * si no cumple se lanza excepción
     * @param nombre
     * @param usuario
     * @throws UsuarioInvalidoException 
     */
    public void modificarNombre(String nombre, Usuario usuario) throws UsuarioInvalidoException {
        validarNombre(nombre);
        usuario.setNombre(nombre);
    }
    
    /**
     * Recibe Apellido y Usuario como parámetro, se valida el nombre y se actualiza
     * si no cumple se lanza excepción
     * @param apellido
     * @param usuario
     * @throws UsuarioInvalidoException 
     */
    public void modificarApellido(String apellido, Usuario usuario) throws UsuarioInvalidoException {
        validarApellido(apellido);
        usuario.setApellido(apellido);
    }
    
    /**
     * Recibe Celular y Usuario como parámetro, se valida el nombre y se actualiza
     * si no cumple se lanza excepción
     * @param celular
     * @param usuario
     * @throws UsuarioInvalidoException 
     */
    public void modificarCelular(String celular, Usuario usuario) throws UsuarioInvalidoException {
        validarCelular(celular);
        usuario.setCelular(celular);
    }
    
    /**
     * Recibe email y Usuario como parámetro, se valida el nombre y se actualiza
     * si no cumple se lanza excepción
     * @param email
     * @param usuario
     * @throws EmailInvalidoException 
     */
    public void modificarEmail(String email, Usuario usuario) throws EmailInvalidoException {
        validarEmail(email);
        usuario.setMail(email);
    }
    
    /**
     * Recibe usuario como parametro y modifica su rol de acuerdo a la lógica posible
     * (por defecto se crean como usuario)
     * @param usuario 
     */
    public void modificarRol(Usuario usuario) {
        if (usuario.getRol() == Rol.USUARIO) {
            usuario.setRol(Rol.ADMIN);
        } else {
            usuario.setRol(Rol.USUARIO);
        }
    }
    
    
    // Métodos de validación
    
    /**
     * Recibe un nombre como parámetro, se valida que no quede vació,
     * si no cumple se lanza excepción
     * @param nombre
     * @throws UsuarioInvalidoException 
     */
    public void validarNombre(String nombre) throws UsuarioInvalidoException {
        if (nombre == null || nombre.isBlank()) {
            throw new UsuarioInvalidoException("El nombre del usuario no puede quedar vacío");
        }
    }

    /**
     * Recibe un Apellido como parámetro, se valida que no quede vació,
     * si no cumple se lanza excepción
     * @param apellido
     * @throws UsuarioInvalidoException 
     */
    public void validarApellido(String apellido) throws UsuarioInvalidoException {
        if (apellido == null || apellido.isBlank()) {
            throw new UsuarioInvalidoException("El apellido del usuario no puede quedar vacío");
        }
    }

    /**
     * Recibe un celular como parámetro, se valida que no quede vació,
     * si no cumple se lanza excepción
     * @param celular
     * @throws UsuarioInvalidoException 
     */
    public void validarCelular(String celular) throws UsuarioInvalidoException {
        if (celular == null || celular.isBlank()) {
            throw new UsuarioInvalidoException("El celular del usuario no puede quedar vacío");
        }
    }

    /**
     * valida que email no quede vacío y que no exista previamente para otro
     * usuario si se incumple una de las condiciones se lanza excepcion
     * personalizada
     * @param email
     * @throws integrador_prog2.exception.EmailInvalidoException
     */
    public void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.isBlank()) {
            throw new EmailInvalidoException("El email del usuario no puede quedar vacío");
        }

        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(email)) {
                throw new EmailInvalidoException("El email ya existe, debe crearse uno único");
            }
        }
    }
    
}
