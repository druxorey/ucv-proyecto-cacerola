<h1 align="center">MiComedorUCV</h1>

<p align="center">Sistema para la gestión eficiente del comedor universitario</p>

<p align="center">
<a href="docs/DEVELOPMENT.md"><img src="https://img.shields.io/badge/development%20guide-BD93F9?style=for-the-badge"></a>
<a href="#instalación"><img src="https://img.shields.io/badge/installation-FF79C6?style=for-the-badge"></a>
<a href="#dependencias"><img src="https://img.shields.io/badge/dependencies-BD93F9?style=for-the-badge"></a>

## Sobre el Proyecto

MiComedorUCV es un sistema de software, programado en Java, diseñado para optimizar y automatizar la gestión del comedor universitario de la UCV. Su objetivo principal es mejorar la eficiencia en los procesos relacionados con estudiantes, empleados y administradores, proporcionando una solución integral que facilite la administración de recursos, la planificación de menús y el control de asistencia.

### Metodologías de Desarrollo

El proyecto comenzó utilizando la metodología RUP (Rational Unified Process) para el modelado inicial y la definición de requerimientos. Posteriormente, se adoptó XP (Extreme Programming) para la implementación y desarrollo iterativo.

Para más detalles, consulta la documentación en la carpeta `docs`:

- [Modelado del Dominio](docs/01-domain-modeling/)
- [Requerimientos](docs/02-requeriments-discipline/)
- [Planificación de Lanzamiento](docs/03-release-planification/)

### Instalación

1. Clona el repositorio:
	```bash
	git clone https://github.com/druxorey/ucv-proyecto-cacerola.git
	cd ucv-proyecto-cacerola
	```

2. Compila el proyecto:
	```bash
	./javac MiComedorUCV.java
	```

3. Ejecuta el proyecto:
	```bash
	java -jar MiComedorUCV.jar
	```

4. Alternativamente, descarga el ejecutable desde la sección [Releases](https://github.com/<usuario>/MiComedorUCV/releases) del repositorio.

### Dependencias

Las dependencias de terceros usadas en el proyecto son:

Dependencia  | Versión | Uso
------------- | ------------- | -------------
JUnit | 1.10.2  | Proporciona las herramietnas necesarias para ejecutar las pruebas unitarias del proyecto.
javax.activation | 1.2.0 | Proporciona clases para manejar datos MIME.
javax.mail | 1.6.2 | Proporciona funcionalidades para enviar y recibir correos electrónicos.


## Equipo de Desarrollo

<p align="center">Equipo 2</p>


| [![Druxorey](https://github.com/druxorey.png?size=400)](https://github.com/druxorey) | [![Apurejose](https://github.com/Apurejose.png?size=1)](https://github.com/Apurejose) | [![Renzower](https://github.com/Renzower.png?size=100)](https://github.com/Renzower) | [![LuiDL314](https://github.com/LuiDL314.png?size=100)](https://github.com/LuiDL314) |
| ------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------ |
| [Guillermo Galavís](https://github.com/druxorey)                                              | [José Apure](https://github.com/Apurejose)                                             | [Renzo Morales](https://github.com/Renzower)                                              | [Luis Lima](https://github.com/LuiDL314)                                              |

## Licencia

Este proyecto está licenciado bajo la Licencia GPL-3.0. Consulte el archivo [LICENSE](LICENSE) para más detalles.
