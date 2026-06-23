package integrador_prog2.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Base {
    
    private Long id;
    private boolean eliminado = false;
    private LocalDateTime createdAt;
    private static Long contadorId = 0L;

    
    public Base() {
        this.createdAt = LocalDateTime.now();
        setId();
    }
    
    
    // Getters

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    // Setters

    private void setId() {
        contadorId += 1L;
        this.id = contadorId;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Base other = (Base) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public abstract String toString();
    
}
