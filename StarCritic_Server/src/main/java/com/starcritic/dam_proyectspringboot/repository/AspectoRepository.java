package com.starcritic.dam_proyectspringboot.repository;

import com.starcritic.dam_proyectspringboot.model.bd.Aspecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Jesús Santos Baquero
 */
@Repository
public interface AspectoRepository extends JpaRepository<Aspecto, Long> {

    /**
     * Metodo para obtener los aspectos de contenidos audiovisuales o videojuegos
     * @param tipo el tipo del cual se desean obtener los aspectos
     * @param ambos para definir si se desean obtener los aspectos en comun o no
     * @return todos los aspectos de ese tipo de contenido
     **/ 
    @Query("SELECT a FROM Aspecto a "
            + "WHERE a.tipoContenido = :tipo OR a.tipoContenido = :ambos "
            + "ORDER BY CASE WHEN a.tipoContenido = :ambos THEN 0 ELSE 1 END, a.nombre")
    List<Aspecto> obtenerAspectosPorTipo(@Param("tipo") Aspecto.Categoria tipo, @Param("ambos") Aspecto.Categoria ambos);
    
    /**
     * Metodo que podrán usar todos los que implementen esta interfaz
     * @return  todos los aspectos que posee un contenido audiovisual
     */
    default List<Aspecto> obtenerAspectosAudiovisual() {
        return obtenerAspectosPorTipo(Aspecto.Categoria.AUDIOVISUAL, Aspecto.Categoria.AMBOS);
    }
    /**
     * Metodo que podrán usar todos los que implementen esta interfaz
     * @return  todos los aspectos que posee un contenido videojuego
     */
    default List<Aspecto> obtenerAspectosVideojuego() {
        return obtenerAspectosPorTipo(Aspecto.Categoria.VIDEOJUEGO, Aspecto.Categoria.AMBOS);
    }
}
