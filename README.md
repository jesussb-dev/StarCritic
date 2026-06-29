# STARCRTIC
Aplicación de críticas de películas, series y videojuegos.
## Índice

---
## Introducción
StarCritic es una aplicación creada en un proyecto final de curso de DAM. Esta aplicación es una aplicación
de escritorio en Java Swing (previsto pasar a Angular o React) que permite el críticar películas, series y videojuegos
a tráves de diferentes apartados, fomentando y creando  críticas a aspectos más concretos del producto.

La crítica por aspectos de un producto es el rasgo diferencial de StarCritic y otras aplicaciones con la misma
función, permitiendole a los usuarios el poder explayarse sobre un aspecto concreto del producto sin afectar a 
a la media general de este mismo.

## Cracterísticas

- **Arquitectura cliente-servidor**: en este repo se encuentra tanto el servidor SpringBoot donde se realizaran todos
los procesos importantes de la aplicación y los datos importantes y la aplicación de escritorio Java Swing.
- Críticas por aspecto: cada producto poseera una serie de aspectos que podrán variar segun el tipe de producto, cada uno
  de estos aspectos tendrá su propia media de valoración.
  
- **Diferencia de roles**: la aplicación es capaz de diferenciar entre distintos tipos de usuarios: anónimo, registrado, crítico profesional
  y administrador. Cada uno de estos roles posee diferentes permisos y funciones dentro de la aplicación.
  
- **Verficación de solictud**: un usuario registrado podra solicitar ser registrado como crítico enviando un documento PDF, este documento se
  guardara en la nube a través de AWS guardando la clave para acceder a el y descargarlo en la base de datos. El administrador tendra la opción
  en la aplicación de visualizar el documento y descargarlo automaticamente de forma temporal.
  
- **Uso de Spring y AWS**: para conectar el cliente al servidor, APIs utilizadas para obtener los datos y a la base de datos local se ha decidido
  usar el framework Spring junto con JPA/Hibernate para acceder a la base de  datos par aplicar todas las operaciones CRUD, tambien se han realizado
  peticiones a dos APIs diferentes (RAWG y OMDb) para obtener las películas, series y videojuegos a criticar. Por último, se ha utilizado AWS para conectar
  la aplicación a un servicio en al nube, específicamente CloudFare R2, esto es para enviar los archivos que la aplicación necesite conservar sin hacer crecer
  de forma desproporcionada a la base de datos local (MySQL).
