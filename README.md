Guayaco Gym - Sistema de Membresías
Descripción
Este es un sistema de gestión de membresías y clientes para un gimnasio, desarrollado con Spring Boot y MySQL. El sistema permite gestionar el registro de clientes, sus membresías y realizar tareas automatizadas como notificaciones de vencimiento de las mismas.

Características principales
Registro y administración de clientes: Permite gestionar el alta, edición y eliminación de miembros del gimnasio.
Gestión de membresías: Administración de las fechas de inicio y vencimiento de las membresías de los clientes.
Notificaciones automáticas: Tareas programadas con Scheduler para enviar notificaciones a los usuarios cuando sus membresías estén próximas a vencer.
Persistencia de datos: Uso de JPA (Java Persistence API) para interactuar con la base de datos MySQL.
Funcionalidad CRUD: Añadir, editar y eliminar miembros y sus respectivas membresías.


Tecnologías utilizadas
Spring Boot: Framework para el desarrollo del backend.
MySQL: Base de datos relacional.
JPA (Java Persistence API): Para la persistencia de datos.
Maven: Herramienta de construcción y gestión de dependencias.
Lombok: Biblioteca para evitar escribir código repetitivo como getters y setters.
Scheduler: Para la programación de tareas automáticas como el envío de notificaciones.


Requisitos
Java 21: Este proyecto está diseñado para ejecutarse con Java 21.
Spring Boot: El marco de trabajo para el desarrollo del backend.
MySQL: La base de datos utilizada es MySQL. Si prefieres usar otro sistema de base de datos, necesitarás hacer los ajustes correspondientes.
Maven: Si no tienes Maven instalado, puedes usar la configuración predeterminada de Spring Boot para gestionar dependencias

Instalación
Clonación del repositorio
Para clonar este repositorio, puedes usar el siguiente comando en tu terminal:

git clone https://github.com/jairalmeida/Sistema-de-Membresias-Guayaco-Gym-con-Spring-Boot.git

Configuración de la base de datos
Crear la base de datos:

Asegúrate de tener MySQL instalado en tu máquina y crea la base de datos guayaco_gym_db en tu servidor MySQL.
Importar la base de datos:

Para importar el archivo de la base de datos (guayaco_gym_db_export.sql), primero descarga el archivo del repositorio en la carpeta database/ del proyecto.

Luego, usa el siguiente comando para importar el archivo a MySQL:

mysql -u usuario -p guayaco_gym_db < database/guayaco_gym_db_export.sql
Reemplaza usuario por el nombre de usuario de tu base de datos MySQL.

Si usas PostgreSQL, puedes importar el archivo con el siguiente comando:


psql -U usuario -d guayaco_gym_db -f database/guayaco_gym_db_export.sql
Nuevamente, reemplaza usuario por tu nombre de usuario de PostgreSQL.

Eso sería todo. Gracias :) 
By Marlon Almeida
