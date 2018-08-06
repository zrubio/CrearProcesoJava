# mongoDB

## Instalación

#### Instalación

```bash
$ sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2930ADAE8CAF5059EE73BB4B58712A2291FA4AD5
$ echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.6 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.6.list
$ sudo apt-get update
$ sudo apt-get install -y mongodb-org
```

#### Iniciar \| parar \| reiniciar

```bash
$ sudo service mongod [start|restart|stop]
```

#### Iniciar MongoDB

```bash
$ mongo --host 127.0.0.1:27017
```

## Mongo SHELL

### DB en uso

```bash
db
```

### Seleccionar una db

```bash
use [database]
```

### Listado de dbs

```bash
show dbs
```

### Listado de collections

```bash
show collections
```

### Crear db y usarla

```bash
use nombreDB
```

### Crear collection

```bash
db.nombreCollection.insertOne(
    {'propiedad':[valor|'valor'],
    ....
    }
);
```

### Consultar collection

```text
db.getCollection("nombreCollection").find();
```



