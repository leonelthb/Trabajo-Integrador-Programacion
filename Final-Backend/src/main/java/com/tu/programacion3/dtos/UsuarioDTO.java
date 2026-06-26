package com.tu.programacion3.dtos;
import com.tu.programacion3.enums.Rol;

public record UsuarioDTO (
        String nombre,
        String email,
        Rol rol
){
}
