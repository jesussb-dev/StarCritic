package com.starcritic.dam_proyectspringboot.model.bd;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Jesús Santos Baquero
 */
@Converter(autoApply = true)
public class EstadoCertificacionConverter implements AttributeConverter<EstadoCertificacion, String> {

    @Override
    public String convertToDatabaseColumn(EstadoCertificacion estado) {
        if (estado == null) return null;
        return estado.getDbValue();
    }

    @Override
    public EstadoCertificacion convertToEntityAttribute(String dbValue) {
        return EstadoCertificacion.fromDbValue(dbValue);
    }
}
