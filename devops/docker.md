# Docker

## Docker

## Desplegando en Docker

### Buscar imagen en Docker

```bash
$ docker search <name>
```

{% hint style="info" %}
Las imágenes se pueden encontrar en: registry.hub.docker.com
{% endhint %}

### Ejecutar un contenedor con la imagen de docker \(foreground\)

```bash
$ docker run <name>
```

La primera vez que se descarga la imagen, lo hará en Docker Host machine

### Ejecutar un contenedor con la imagen de docker \(background\)

```bash
$ docker run -d <name>
```

### Ejecutar un contenedor con la última versión de la imagen \(foreground\)

```bash
$ docker run -d <name>:latest
```

### Listar contenedores que están ejecutándose

```bash
$ docker ps
CONTAINER ID    IMAGE           COMMAND                  CREATED         STATUS       PORTS       NAMES
fed7b9519e04    redis:latest    "docker-entrypoint.s…"   8 minutes ago   Up 8 minutes 6379/tcp    jolly_tesla
```

### Consultar detalles de un contenedor ejecutándose

```bash
$ docker inspect <containerId / friendlyName>
```

### Consultar mensajes que el contenedor ha escrito en la salida estándar

```text
$ docker logs <containerId / friendlyName>
```

### Acceder al contenedor

Si un servicio necesita ser accedido por un proceso que no se está ejecutando en el contenedor, el puerto necesitará estar abierto por el Host.

Los puertos son limitados cuando los contenedores se ejecutan utilizando:

```bash
-p <host-port>:<container-port>
```

La mejor manera de arreglarlo es definiendo los puertos cuando vaya a ejecutarse el contenedor:

```text
$ docker run -d --name redisHostPort -p 6379:6379 redis:latest
```

Por defecto, el puedo en el host está mapeado a 0.0.0.0 \(todas las direcciones IP\). Se puede especificar una IP en el mapeado de puertos: 

```bash
$ docker run -d --name redisHostPort -p 127.0.0.1:6379:6379
```

El problema con la ejecución de procesos en un puerto fijo es que sólo puede ejecutar una única instancia. Para ejecutar múltiples instancias deberá configurar la aplicación dependiendo de qué puerto se esté ejecutando.

Utilizando la opción "-p 6379" se habilita un puerto aleatorio. 

```bash
$ docker run -d --name redisDynamic -p 6379 redis:latest
```

Para conocer en qué puerto se está ejecutando:

```bash
$ docker port redisDynamic 6379
```

### Persistencia de los datos

Los contenedores no tienen estado. Así que cuando se elimina un contenedor, toda la información se eliminará. Para hacerla persistente es necesario enlazar el contenedor con alguna unidad de disco o volumen:

Cuando el directorio está montado, los archivos que existen en el directorio del host pueden ser accedidos por el contenedor. Cualquier dato modificado en el directorio dentro del contenedor se almacenará dentro del host.

```bash
-v <host-dir>:<container-dir>
```

Ejemplo:

```bash
docker run -d --name redisMapped -v /opt/docker/data/redis:/data redis
```

{% hint style="info" %}
Docker permite el uso del comando $pwd \(directorio actual\)
{% endhint %}

### Interactuar con el contenedor \(foreground\)

```bash
# Ejecuta un contenedor Ubuntu y ejecuta el comando PS
$ docker run ubuntu ps
```

```bash
# Obtener acceso con la consola dentro de un contenedor
$ docker run -it ubuntu bash
```

## Dockerfile

Define todos los pasos necesarios para crear una imagen con Docker configurada para ejecutarse como un contenedor. La imagen contiene en sí misma un SO y la configuración necesaria para poder ejecutar la aplicación.

Todas las imágenes de Docker empiezan con una imagen base. Las imágenes base son las imágenes del Docker Registry utilizadas para iniciar contenedores. 

Dockerfile está escrito con DSL \(Domain Specific Language\)

{% code-tabs %}
{% code-tabs-item title="Dockerfile" %}
```text
# Utilizar la imagen OpenJDK 8
FROM oracle/openjdk:8
# Definir el directorio de trabajo
WORKDIR /app
# Copiar el contenido de la carpeta actual a /app (contenedor)
COPY . /app
# Compila el código fuente de Hello.java
RUN javac Hello.java
# Puerto interno del contenedor
EXPOSE 80
# Ejecución del programa compilado pasando un argumento
CMD ["java", "Hello"]
```
{% endcode-tabs-item %}

{% code-tabs-item title=undefined %}
```
FROM 
```
{% endcode-tabs-item %}
{% endcode-tabs %}

### Instrucciones

#### FROM

Inicializa una nueva construcción con una imagen base.

{% code-tabs %}
{% code-tabs-item title="Dockerfile" %}
```text
# Método 1
FROM <imagen> [AS <nombre>]
# Método 2
FROM <imagen>[:<tag>] [AS <nombre>]
# Método 3
FROM <imagen>[@<digest>] [AS <nombre>]
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% code-tabs %}
{% code-tabs-item title="Dockerfile" %}
```text
FROM oracle/openjdk:8
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### RUN

Ejecuta cualquier comando en una nueva capa por encima de la imagen actual y hace _commit_ de los resultados.

```text
# Método 1
# 1 Linux /bin/sh -c
# 2 Windows cmd /S /C
RUN <comando>
# Método 2
RUN ["executable","parametro1","parametro2"]
```

```bash
RUN /bin/bash -c 'javac Hello.java'
```

#### CMD

#### LABEL

#### EXPOSE

#### ENV

#### ADD

#### COPY

#### ENTRYPOINT

#### VOLUME

#### USER

#### WORKDIR

#### ARG

#### ONBUILD

#### STOPSIGNAL

#### HEALTHCHECK

#### SHELL

## Docker compose

