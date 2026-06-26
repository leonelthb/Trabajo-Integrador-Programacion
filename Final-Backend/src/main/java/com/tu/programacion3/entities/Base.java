package com.tu.programacion3.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor


// indicar que esta clase no necesita persistirla
@MappedSuperclass
public class Base {
    // clave primaria
    @Id
    // Auto incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    public Base(Long id){
        this.id= id;
        this.createdAt = LocalDateTime.now();
        this.eliminado = false;
    }

    @Override
    public String toString() {
        return "id=" + id + ", eliminado=" + eliminado + ", createdAt=" + createdAt;
    }
}


