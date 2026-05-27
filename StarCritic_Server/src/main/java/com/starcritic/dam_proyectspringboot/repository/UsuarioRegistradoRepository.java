package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.UsuarioRegistrado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRegistradoRepository extends JpaRepository<UsuarioRegistrado, Long> {
    /**
     * Busca el usuario y si no existe devuelve null por Optional
     * @param nombreUsuario el nombre del usuario a buscar
     * @return el usuario en caso de que exista
     */
    Optional<UsuarioRegistrado> findByNombreUsuario(String nombreUsuario);
}
