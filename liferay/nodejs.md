# NodeJS

## NVM - Gestor de versiones NODE

Se recomienda desinstalar cualquier versión instalada de nodejs.

Para instalar NVM

Linux:

```bash
$ sudo apt-get update
$ sudo apt-get install build-essential
$ curl -o- 
https://raw.githubusercontent.com/creationix/nvm/v0.33.8/install.sh | 
bash
```

Resultado de la ejecución:

```bash
% Total  % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 12540  100 12540    0     0  26704      0 --:--:-- --:--:-- --:--:-- 26737
=> nvm is already installed in /home/zrubio/.nvm, trying to update using git
=> => Compressing and cleaning up git repository

=> nvm source string already in /home/zrubio/.bashrc
=> bash_completion source string already in /home/zrubio/.bashrc
=> Close and reopen your terminal to start using nvm or run the following to use it now:

export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
```

### Listar versiones disponibles

```bash
$ nvm ls-remote
```

### Instalar 

```bash
$ nvm install VERSION
```

Resultado de la ejecución

```bash
$ nvm install 10.5.0
Downloading and installing node v10.5.0...
Downloading https://nodejs.org/dist/v10.5.0/node-v10.5.0-linux-x64.tar.xz...
######################################################################## 100,0%
Computing checksum with sha256sum
Checksums matched!
nvm is not compatible with the npm config "prefix" option: currently set to "/home/zrubio/.npm-packages"
Run `nvm use --delete-prefix v10.5.0` to unset it.
```



### Listado de versiones instaladas

```bash
$ nvm ls
```

### Cambiar de versión 

```bash
$ nvm use [alias|version]
```

### Definir alias para distintas versiones 

```bash
$ nvm alias deadpool 10.5.0
# Utilizar version deadpool
$ nvm use deadpool
```

### Versión actual

```bash
$ nvm current
```

### Path de la versión instalada

```bash
$ nvm which deadpool
```

## Comandos

### Instalar

```bash
$ npm install npm@latest -g
```

## Crear proyecto NodeJS

Para crear un proyecto en NodeJS, crearemos un directorio y ejecutaremos 'npm init' para que nos cree el archivo package.json, donde se encuentran todas las propiedades del proyecto, desde su nombre, versión hasta los paquetes necesarios, ...

```bash
$ mkdir myApp
$ cd myApp 
$ npm init
```

### package.json

El archivo que creamos con el comando "npm init' tiene el siguiente contenido:

```bash
$ npm init
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help json` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (myjhapp) myApp
Sorry, name can no longer contain capital letters.
package name: (myjhapp) 
version: (1.0.0) 
description: Example NodeJS app
entry point: (index.js) 
test command: 
git repository: 
keywords: 
author: zrubio
license: (ISC) 
About to write to /home/zrubio/LEARN/myJHApp/package.json:

{
  "name": "myjhapp",
  "version": "1.0.0",
  "description": "Example NodeJS app",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "zrubio",
  "license": "ISC"
}


Is this ok? (yes) 
```

Contenido:

{% code-tabs %}
{% code-tabs-item title="package.json" %}
```javascript
{
  "name": "myjhapp",
  "version": "1.0.0",
  "description": "Example NodeJS app",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "zrubio",
  "license": "ISC"
}

```
{% endcode-tabs-item %}
{% endcode-tabs %}

### Ejecutar proyecto

```bash
$ node index.js
```

### Instalar paquetes

Cuando se instalan paquetes, estos se almacenan en el directorio "node\_modules" dentro del proyecto. Si ésta carpeta no está creada, se creará automáticamente con la instalación del primer paquete.

#### Paquetes para el proyecto

```bash
$ npm install PAQUETE
```

Cada paquete que se instala se añade en package.json y package-lock.json, donde figuran todas las dependiencias e información del proyecto/módulo. Package-lock.json está hecho para compartirlo con miembros de desarrollo para tener la misma instalación.

#### Paquetes para utilizarlos como comandos \(global\)

```bash
$ npm install -g PAQUETE
```

#### Paquetes únicamente para el desarrollo

```bash
$ npm install --save-dev PAQUETE
```

Estas dependencias aparecen en la propiedad: "devDependencies"

{% code-tabs %}
{% code-tabs-item title="package.json" %}
```javascript
...
"devDependencies": {
    "paquete":"version"
}
...
```
{% endcode-tabs-item %}
{% endcode-tabs %}

### Definición de scripts package.json

{% code-tabs %}
{% code-tabs-item title="package.json " %}
```javascript
"scripts": {
    "debug": "node --inspect ./bin/www",
    "start-dev": "nodemon ./bin/www",
    "start": "node ./bin/www"
  }
```
{% endcode-tabs-item %}
{% endcode-tabs %}

Ejecutar la aplicación con los diferentes scripts

```bash
$ npm run-script debug
$ npm run debug
```

## ExpressJS - Express Application Generator

### Install

```bash
$ npm install express
# Otra manera
$ npm install express-generator -g
```

### Crear proyecto

```bash
$ express miaplicacion
```

Éste creará un directorio con un package.json. Para instalar las dependencias automáticamente que figuran en éste:

```bash
$ cd miaplicacion
$ npm install
```

### Ejecutar proyecto

```bash
$ SET DEBUG=miaplicacion:* npm start
```

## Mongoose

MongoDB Object Model for NodeJS

#### Instalación

```bash
$ npm install mongoose
```

#### Importar Mongoose al proyecto

```javascript
var mongoose = require('mongoose');
```

#### Conectar con MongoDB

```javascript
mongoose.connect('mongodb://localhost/heroes');
```

