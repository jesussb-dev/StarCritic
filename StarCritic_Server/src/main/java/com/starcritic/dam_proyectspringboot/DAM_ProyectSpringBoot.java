/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.starcritic.dam_proyectspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada principal de la aplicación Spring Boot del servidor StarCritic.
 * Arranca el contexto de Spring y publica los endpoints REST de la API.
 * @author Jesús Santos Baquero
 */
@SpringBootApplication
public class DAM_ProyectSpringBoot {

    /**
     * Metodo principal que arranca la aplicación Spring Boot.
     * @param args los argumentos pasados desde la linea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(DAM_ProyectSpringBoot.class, args);
    }
}
