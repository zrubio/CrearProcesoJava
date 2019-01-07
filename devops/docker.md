# Docker

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



