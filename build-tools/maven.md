# Maven

### Estructura

* Project Base Folder
  * pom.xml
  * src
    * main
      * java
      * resources
    * test
      * java
      * resources
  * target

Maven espera encontrar el código fuente java en la ruta "src/main/java"

### Project Object Model \(POM.XML\)

Define todos los metadatos del proyecto

Coordinates es como Maven identifica unívocamente los componentes del build haciendo uso de la combinación del groupName, artifactName y el número de la versión.

También se declaran las dependencias

Configuraciones del Build.

Soporta la herencia.

### Building blocks

#### Goals

Es el nivel más bajo de trabajo. Equivalente a una tarea en Ant. Por ejemplo: empaquetar un JAR.

El nombre del goal es la combinación del nombre de plugin de Maven y un nombre específico para el goal en el plugin.

El goal es compila y forma parte del plugin compilar.

```bash
$ mvn compiler:compile
```

#### Phases



