<h1 align="center">MiComedorUCV</h1>

<p align="center">Sistema para la gestión eficiente del comedor universitario</p>

<p align="center">
<a href="docs/DEVELOPMENT.md"><img src="https://img.shields.io/badge/development%20guide-BD93F9?style=for-the-badge"></a>
<a href="#instalación"><img src="https://img.shields.io/badge/installation-FF79C6?style=for-the-badge"></a>
<a href="#dependencias"><img src="https://img.shields.io/badge/dependencies-BD93F9?style=for-the-badge"></a>

> ⚠️ **Advertencia**: Este proyecto fue desarrollado como un ejercicio rápido para aprender Java y explorar el patrón de diseño MVC. Es un boceto inicial y no representa prácticas de desarrollo recomendadas ni código de producción.

## Sobre el Proyecto

MiComedorUCV es un sistema de software, programado en Java, diseñado para optimizar y automatizar la gestión del comedor universitario de la UCV. Su objetivo principal es mejorar la eficiencia en los procesos relacionados con estudiantes, empleados y administradores, proporcionando una solución integral que facilite la administración de recursos, la planificación de menús y el control de asistencia.

![Application Screenshot](/docs/banner.webp)

### Metodologías de Desarrollo

El proyecto comenzó utilizando la metodología RUP (Rational Unified Process) para el modelado inicial y la definición de requerimientos. Posteriormente, se adoptó XP (Extreme Programming) para la implementación y desarrollo iterativo.

### Instalación

1. Descarga el ejecutable desde la sección [Releases](https://github.com/druxorey/ucv-proyecto-cacerola/releases) del repositorio.

#### Windows

1. Asegúrate de tener instalado el Java Development Kit (JDK). Puedes descargarlo desde [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/).

2. Ejecuta el archivo descargado directamente o usa el siguiente comando en la terminal:

	```bash
	java -jar MiComedorUCV-X.X.X.jar
	```

#### Linux

1. Asegúrate de tener instalado el JDK correspondiente a tu distribución:
	- En Arch Linux: instala el paquete `jdk-openjdk` usando `sudo pacman -S jdk-openjdk`.
	- En Debian/Ubuntu: instala el paquete `openjdk-24-jdk` usando `sudo apt install openjdk-24-jdk`.

2. Ejecuta el archivo descargado con el siguiente comando:

	```bash
	java -jar MiComedorUCV-X.X.X.jar
	```

### Dependencias

Las dependencias de terceros usadas en el proyecto son:

Dependencia          | Versión    | Uso
---------------------|------------|-------------------------------------------------
JUnit               | 4.13.2     | Proporciona las herramientas necesarias para ejecutar las pruebas unitarias del proyecto.
javax.activation    | 1.2.0      | Proporciona clases para manejar datos MIME.
javax.mail          | 1.6.2      | Proporciona funcionalidades para enviar y recibir correos electrónicos.
hamcrest-core       | 1.3        | Proporciona matchers para realizar pruebas unitarias.
json-simple         | 1.1.1      | Proporciona una biblioteca ligera para trabajar con JSON.


## Licencia

Este proyecto está licenciado bajo la Licencia GPL-3.0. Consulte el archivo [LICENSE](LICENSE) para más detalles.
