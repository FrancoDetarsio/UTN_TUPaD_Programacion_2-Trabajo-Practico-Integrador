package integrador_prog2.entities;

import integrador_prog2.enums.Rol;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base {
    
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario(String nombre, String apellido, String mail, String celular) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.rol = rol.USUARIO; // por defecto (se modifica en editar)
        this.pedidos = new ArrayList<>();
    }
    
    
    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }

    public Rol getRol() {
        return rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMail(String mail) {
        if (mail != null && !mail.isBlank()) {
            this.mail = mail;
        }
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    
    // Métodos
    
    public void agregarPedido(Pedido p) {
        if (p != null && !pedidos.contains(p)) {
            pedidos.add(p);
            p.setUsuario(this);
        }
    }
    
    
    @Override
    public String toString() {
        return String.format("#%d | %s %s | email: %s | Rol: %s", getId(), nombre, apellido, mail, getRol());
    }

}
