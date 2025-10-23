# MiniTienda Riwi

MiniTienda Riwi es una aplicación Java que gestiona un inventario de productos con una interfaz gráfica simple utilizando **JOptionPane**. La aplicación permite agregar productos, realizar compras, consultar estadísticas y buscar productos dentro del inventario. La gestión de datos se realiza mediante **ArrayList**, **HashMap** y **Array**.

## Objetivo

El propósito de esta aplicación es combinar **Programación Orientada a Objetos (POO)** y **Estructuras de Datos** con una interfaz gráfica simple usando **JOptionPane** para permitir la interacción con el usuario.

### Historia de Usuario
1. **Modelo de Datos**: Se gestiona un inventario con nombres de productos, precios y stock utilizando estructuras de datos como **ArrayList**, **HashMap** y **Array**.
2. **Interactividad**: A través de un menú interactivo, el usuario puede agregar productos, listar el inventario, realizar compras, consultar estadísticas y buscar productos por nombre.
3. **Validaciones**: Se validan entradas del usuario y se gestionan excepciones (como duplicados de productos o stock insuficiente).

## Estructura del Proyecto

El proyecto se organiza en las siguientes carpetas y archivos:

MiniTiendaRiwi/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── codeup/
│   │   │   │   │   ├── minitiendariwi/
│   │   │   │   │   │   ├── domain/                          # Contiene las clases del modelo
│   │   │   │   │   │   │   ├── Alimento.java
│   │   │   │   │   │   │   ├── Electrodomestico.java
│   │   │   │   │   │   │   ├── Producto.java
│   │   │   │   │   │   │   └── InventarioModelo.java          # Lógica de negocio e inventario
│   │   │   │   │   │   ├── exception/                         # Excepciones personalizadas
│   │   │   │   │   │   │   ├── ProductoYaExisteException.java
│   │   │   │   │   │   │   └── StockInsuficienteException.java
│   │   │   │   │   │   ├── service/                           # Lógica del servicio
│   │   │   │   │   │   │   └── TiendaServicio.java            # Métodos de control de tienda (agregar, comprar, etc.)
│   │   │   │   │   │   ├── ui/                                # Interfaz de usuario (JOptionPane)
│   │   │   │   │   │   │   └── TiendaVista.java               # Muestra los diálogos de interacción con el usuario
│   │   │   │   │   │   ├── MiniTiendaRiwi.java                # Clase principal que corre el programa
│   │   │   │   │   │   └── pom.xml                           # Archivo Maven de configuración
│   │   └── test/                                              # Si tienes pruebas, se podrían agregar aquí
│   └── target/                                                 # Archivos compilados (serán generados automáticamente)
├── README.md                                                   # Este archivo, describiendo tu proyecto
└── .gitignore                                                   # Ignora archivos que no deberían subirse a Git (por ejemplo, target/)



### **Descripción de las carpetas:**
- **domain/**: Contiene las clases del modelo, como `Producto`, `Alimento`, `Electrodoméstico` e `InventarioModelo`.
- **exception/**: Contiene las excepciones personalizadas `ProductoYaExisteException` y `StockInsuficienteException`.
- **service/**: Contiene la lógica principal de la tienda en `TiendaServicio`.
- **ui/**: Contiene la clase `TiendaVista`, que maneja la interacción con el usuario a través de JOptionPane.
- **MiniTiendaRiwi.java**: La clase principal que inicia la aplicación.

## Funcionalidades Implementadas

1. **Agregar producto**: Permite agregar productos al inventario y asegura que no haya duplicados.
2. **Listar inventario**: Muestra todos los productos en el inventario con su nombre, precio, stock y descripción.
3. **Comprar producto**: Permite al usuario comprar productos, asegurando que haya suficiente stock y actualizando el inventario.
4. **Estadísticas**: Muestra los productos más baratos y más caros en el inventario.
5. **Buscar producto**: Permite buscar productos por nombre (con coincidencias parciales).
6. **Ticket final**: Al salir, muestra el total acumulado de ventas en la sesión.

## Validaciones

- **Manejo de Excepciones**:
  - `NumberFormatException`: Para manejar entradas no válidas (por ejemplo, al ingresar precio o cantidad).
  - `ProductoYaExisteException`: Lanza una excepción si el producto ya existe al intentar agregarlo al inventario.
  - `StockInsuficienteException`: Lanza una excepción si no hay suficiente stock para una compra.

- **Validaciones**:
  - No se permiten **valores vacíos** ni **negativos** para precios y cantidades.
  - Los **productos duplicados** no pueden ser agregados.
  - La **cantidad de compra** debe ser válida y estar disponible en el stock.

## Requisitos

Para ejecutar este proyecto necesitas tener instalado:

- **Java 11 o superior**.
- **IDE** como IntelliJ IDEA o Eclipse para abrir el proyecto.
- **Maven** (opcional, para construir el proyecto).

## Instrucciones para ejecutar

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/tu-usuario/MiniTiendaRiwi.git


Abre el proyecto en tu IDE favorito (IntelliJ, Eclipse, etc.).

Ejecuta MiniTiendaRiwi.java para comenzar la aplicación.

Ramas del Proyecto

El proyecto se maneja utilizando git y se organiza en las siguientes ramas:

main: Contiene la versión final lista para producción.

develop: Rama de desarrollo donde se realizan las nuevas implementaciones y cambios.

Contribuciones

Si deseas contribuir al proyecto, por favor sigue estos pasos:

Haz un fork del repositorio.

Crea una nueva rama (git checkout -b feature-nueva-funcionalidad).

Realiza tus cambios y haz commit (git commit -am 'Añadir nueva funcionalidad').

Haz push a tu rama (git push origin feature-nueva-funcionalidad).

Abre un Pull Request explicando tus cambios.

Licencia

Este proyecto está licenciado bajo la MIT License.

Tareas Realizadas
TASK 1: Modelo de datos

Se implementó Producto como clase abstracta.

Se crearon las subclases Alimento y Electrodoméstico.

Se implementaron las estructuras de datos ArrayList, HashMap, Array para manejar inventario, stock y precios.

TASK 2: Menú principal con JOptionPane

Menú interactivo que permite agregar productos, listar inventario, realizar compras, ver estadísticas, buscar productos y mostrar el ticket final.

TASK 3: Flujo de cada opción

Validaciones para agregar productos, comprar productos y mostrar estadísticas.

Opción de búsqueda con coincidencias parciales.

TASK 4: Validaciones y mensajes

Manejo de excepciones (NumberFormatException, ProductoYaExisteException, StockInsuficienteException).

Uso de showMessageDialog para mostrar mensajes de confirmación y error.
