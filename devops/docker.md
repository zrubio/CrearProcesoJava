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



