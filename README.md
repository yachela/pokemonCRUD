# Proyecto Pokémon Trainer

## Navegación
- [Requisitos](#requisitos)
- [Inicialización de la Base de Datos](#inicialización-de-la-base-de-datos)
  - [Método 1: Desde la Consola de H2](#método-1-desde-la-consola-de-h2)
  - [Método 2: Automáticamente desde Java](#método-2-automáticamente-desde-java)
- [Hitos](#hitos)
  - [Hito 1: Modelo Básico](#hito-1-modelo-básico)
  - [Hito 2: Interacciones Avanzadas](#hito-2-interacciones-avanzadas)
  - [Hito 3: Usuarios y Entrenadores](#hito-3-usuarios-y-entrenadores)
  - [Hito Final: Creación de Entrenadores](#hito-final-creación-de-entrenadores)
- [Descripción del Modelo](#descripción-del-modelo)
  - [Tablas de la Base de Datos](#tablas-de-la-base-de-datos)
- [Flujo del Usuario](#flujo-del-usuario)
  - [1. Inicio de Sesión](#1-inicio-de-sesión)
  - [2. Menú Principal](#2-menú-principal)
  - [3. Flujo de Registro y Gestión](#3-flujo-de-registro-y-gestión)
  - [4. Flujo de Batalla](#4-flujo-de-batalla)

---

## Requisitos

- **Java JDK 17** o superior.
- **Maven** para gestionar dependencias.
- **Consola H2** para inicializar la base de datos (opcional).

---

## Inicialización de la Base de Datos

### Método 1: Desde la Consola de H2
1. Inicia la consola H2 con el siguiente comando:

   ```
   java -cp h2-2.1.214.jar org.h2.tools.Console
   ```

2. Conéctate usando las credenciales:
  - **URL**: `jdbc:h2:tcp://localhost/~/test`
  - **Usuario**: `sa`
  - **Contraseña**: *(dejar en blanco)*

3. Carga el archivo `initialize_database.sql` ubicado en `src/main/resources/sql` y ejecútelo.

### Método 2: Automáticamente desde Java

El script SQL se ejecutará automáticamente al iniciar la aplicación. No se requiere acción manual.

---

## Hitos

### Hito 1: Modelo Básico
- Entrenadores y Pokémon.
- Captura de Pokémon.
- Lógica de batalla básica.

### Hito 2: Interacciones Avanzadas
- Daños basados en tipos de Pokémon.
- CRUD para todas las entidades.

### Hito 3: Usuarios y Entrenadores
- Relación entre usuarios y entrenadores.
- Límite de 3 entrenadores por usuario.
- Batallas aleatorias entre usuarios.

### Hito Final: Creación de Entrenadores
- Un usuario puede crear un entrenador desde la UI.

---

## Descripción del Modelo

### Tablas de la Base de Datos

1. **users**
  - `id` (INT, PK): Identificador del usuario.
  - `name` (VARCHAR): Nombre del usuario.
  - `phone` (VARCHAR): Teléfono del usuario.
  - `password` (VARCHAR): Contraseña del usuario.

2. **trainer**
  - `id` (INT, PK): Identificador del entrenador.
  - `name` (VARCHAR): Nombre del entrenador.
  - `birth_date` (DATE): Fecha de nacimiento.
  - `nationality` (VARCHAR): Nacionalidad.
  - `user_id` (INT, FK): Relación con el usuario.

3. **pokemon**
  - `id` (INT, PK): Identificador del Pokémon.
  - `type` (VARCHAR): Tipo del Pokémon.
  - `energy` (FLOAT): Energía del Pokémon.
  - `power` (INT): Poder del Pokémon.
  - `specie` (VARCHAR): Especie del Pokémon.
  - `trainer_id` (INT, FK): Relación con el entrenador.

4. **user_trainer**
  - `user_id` (INT, FK): Relación con el usuario.
  - `trainer_id` (INT, FK): Relación con el entrenador.

---

## Flujo del Usuario

### 1. Inicio de Sesión
- **Login**:
  - Los usuarios registrados pueden iniciar sesión ingresando su nombre de usuario y contraseña.
  - Si los datos son válidos, son redirigidos al menú principal.
- **Registro**:
  - Los nuevos usuarios pueden registrarse desde la pantalla de inicio, proporcionando su nombre, teléfono y una contraseña.

### 2. Menú Principal
Una vez que el usuario ha iniciado sesión, tendrá acceso a las siguientes opciones:
- **Crear Entrenador**:
  - Los usuarios pueden crear hasta 3 entrenadores, especificando su nombre, fecha de nacimiento y nacionalidad.
- **Ver Entrenadores**:
  - Permite visualizar una lista de entrenadores asociados al usuario actual.
- **Agregar Pokémon**:
  - Los usuarios pueden asignar Pokémon a sus entrenadores, definiendo el tipo, especie, poder y energía.
- **Iniciar Batalla**:
  - Permite enfrentarse a otro usuario aleatorio en una batalla Pokémon. El ganador se determina automáticamente en función de los atributos de los Pokémon.

### 3. Flujo de Registro y Gestión
- **Registrar un Entrenador**:
  1. Selecciona la opción "Crear Entrenador".
  2. Ingresa los datos requeridos: nombre, fecha de nacimiento y nacionalidad.
  3. Guarda el entrenador.
- **Agregar un Pokémon**:
  1. Selecciona la opción "Agregar Pokémon".
  2. Asocia un Pokémon con uno de los entrenadores disponibles.
  3. Define el tipo, poder, energía y especie del Pokémon.

### 4. Flujo de Batalla
- Al seleccionar la opción "Iniciar Batalla", el sistema:
  1. Busca un usuario aleatorio con entrenadores registrados.
  2. Solicita que el usuario elija un entrenador y uno de sus Pokémon.
  3. Simula la batalla entre los Pokémon seleccionados, aplicando las reglas de daño por tipo.
  4. Muestra el ganador al finalizar.

---
