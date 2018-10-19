# Javascript

* Cada objeto tiene la propiedad _constructor,_ que es la función que ha utilizado para poder crear el objeto.
* Las funciones también son objetos.

Maneras de crear objetos con JS

1. [Factory function](../#factory-function)
2. [Constructor](../#constructor)

## Constantes y variables

### Constantes

#### const

```javascript
const PI = 3.1415;
const objeto = {
    id: 1,
    cadena: 'cadena',
    funcion = function() {
        console.log(this.id);
    }
}
```

### Variables

#### let

* Permite declarar variables con cierto permiso de acceso.
* Solo a nivel local

```javascript
function User(id, name) {
    let id = 1;
    this.name = name;
    this.showUser = function() {
        console.log(user);
    }
}
```

#### var

* Permite declarar variables a nivel global
* Permite declarar variables con acceso público.
* Localizable en el objeto Window.

```javascript
function User(name) {
    var id = 1;
    this.name = name;
    this.showUser = function() {
        console.log(user);
    }
}
```

## Tipos de datos

1. **Por valor \(primitivos\):**
   1. number
   2. string
   3. boolean
   4. symbol
   5. undefined
   6. null
2. **Por referencia:**
   1. object
   2. function
   3. array

## Objetos

### Definición de objetos

```javascript
// Versión 1
var objeto = {};

// Versión 2
var objeto = {
    numero = 1,
    nombre = 'nombre',
    funcion = function(){
        console.log('algo');
    }
}
```

### Factory function

```javascript
function newUser(user) { 
    return { 
        user, 
        showUser: function() {
         console.log(user); 
         } 
     };
}
var u = newUser('hola');
u.showUser();
```

### Constructor

```javascript
function User(name) {
    this.name = name;
    this.showUser = function() {
        console.log(user);
    }
}

// Llamada versión 1
var user1 = new User('Mariano');
// Llamada versión 2
User.call({},'Mariano');
// Llamada versión 3. Como la segunda, pero aquí se le pasa como argumento un array
User.apply({},['Mariano', 'Maria']);
```

{% hint style="info" %}
Si no se utiliza **new,** apunta al objeto global **Window**
{% endhint %}

### Propiedades y métodos

#### Getters & setters

```javascript
function User() {
    let name = 'Mariano'
    Object.defineProperty(this, 'id', { 
        get: function() { 
            return id; 
        }, 
        set: function (value) {
             id = value; 
         } 
     });
}
var usuario =  new User();
usuario.name = 'Maria';
console.log(usuario.name);
```

#### Añadir propiedades

```javascript
function User(name) {
    this.name = name;
    this.showUser = function() {
        console.log(user);
    }
}

// Llamada versión 1
var user1 = new User('Mariano');

// Añadir propiedad
user1.code = 1;
user1['postal-code'] = 12345;
```

#### Eliminar propiedades

```javascript
function User(name) {
    this.name = name;
    this.showUser = function() {
        console.log(user);
    }
}

// Llamada versión 1
var user1 = new User('Mariano');
delete user1['name'];
```

#### Mostrar propiedades y métodos del objeto

```javascript
function User(name) {
    this.id=1;
    this.name = name;
    this.showUser = function() {
        console.log(user);
    }
}

// Llamada versión 1
var user = new User('Mariano');

for(let key in user) {
    console.log(key, user[key]);
}
```

#### Mostrar propiedades del objeto

```javascript
for(let key in user) {
    if(typeof user[key] !== 'function')
        console.log(key, user[key]);
}
```

#### Mostrar llaves del objeto

```javascript
// Devuelve un array
var keys = Object.keys(user);
```

#### Verificar si existe una propiedad o método en el objeto

```javascript
if('name' in user)
    console.log('El objeto user tiene la propiedad name');
```

## Herencia

* En JS no hay clases, sólo objetos

### Tipo de herencia

* **Clásica**. Por clases
* **Prototípica**. Por objetos. Cada objeto en JS, tiene el objeto prototype como objetoBase o un objeto padre.
  * Cada objeto tiene la propiedad Constructor que llama a la función 

### Herencia prototípica

* Cuando se accede a una propiedad o a un método de un objeto, JS primero lo busca en el objeto en sí mismo, y si no lo puede encontrar, lo busca en el prototipo del objeto y si no lo encuentra, lo busca en el objeto raíz 
* Prototype es sólo un objeto en memoria. No tiene nada de especial.
* Cada objeto tiene su prototipo o padre, excepto el objeto raíz.

### Herencia múltiple

Los objetos creados con un constructor personalizado tendrán el mismo prototipo. Por ejemplo,

El objeto **usuario** heredará del objeto **usuarioBase** y éste de **prototype** \(el objeto de todos los objetos\).

### Métodos

#### Obtener el prototipo de un objeto

```javascript
let x = {};
Object.getPrototypeOf(x);

```

#### Verificar si varios objetos provienen del mismo prototipo

```javascript
let x = {};
let y = {};
Object.getPrototypeOf(x) === Object.getPrototypeOf(y);
// true
```

{% hint style="info" %}
No se utiliza la propiedad objeto**.\_\_proto\_\_** porque es **deprecated**. Sólo se utiliza por motivos de depuración en consola.
{% endhint %}

#### Descriptores de las propiedades

```javascript
let usuario = { nombre: 'Mariano' };
// Devuelve el objeto prototipo
let objetoBase = Object.getPrototypeOf(usuario);
// Devuelve los descriptores de una propiedad
// Objeto del que quieres obtener la propiedad, y la propiedad que se quiera obtener
let descriptor = Object.getOwnPropertyDescriptor(objetoBase, 'toString');
```

{% hint style="info" %}
**Configurable**: { true \| false } Podemos eliminar este miembro.

**Enumerable**:  { true \| false } Podemos iterar entre los diversos miembros.

**Writable**: { true \| false } Podemos sobreescribir este método
{% endhint %}

#### Definir propiedades con los descriptores

```javascript
let usuario = { nombre: 'Mariano' };
Object.defineProperty(usuario, 'name', {
    writable: false
});
```

### Constructores

#### Añadir propiedades a los prototipos de los constructores

```javascript
Usuario.prototype.saludar = function() {
    console.log('hola')
});
```

## Herencia prototípica

### Crear herencia prototípica

```javascript
function Vehicle() {}
function Car() {}

Car.prototype = Object.create(Vehicle.prototype);
```

### Restablecer el constructor

```javascript
Car.prototype.constructor = Car;
```

### Llamar al superconstructor

```javascript
function Bus(color) {
    Vehicle.call(this, color);
}
```

### Sobre escritura de métodos

```javascript
Vehicle.prototype.sonido = function() {}
Car.prototype.sonido = function() {
    Vehicle.prototype.sonido.call(this);
}
```

### Mixins

Permite combinar múltiples objetos e implementar la composición

```javascript
const felinos = {
  gato: function () {}  
};
const caninos = {
  perro: function () {}
};

function mixin (target, ...sources) {
  // Copia todas las propiedades de los objetos origen 
  // para asignarlos a los objetos destino
  Object.assign(target, ...source);
}

function Animal() {}

mixin(Animal.prototype, felinos, caninos);
```

## Clases

* Son una mejora sintáctica para la herencia basada en prototipos.
* No es un nuevo modelo de herencia orientada a objetos

### Definición

#### Clase anónima

```javascript
class Vehiculo {
    constructor(marca, modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }
};
```

#### Clase nombrada

```javascript
var Vehiculo = class Vehiculo {
    constructor(marca, modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }
}
```

### Acceder a la clase

```javascript
var coche1 = new Vehiculo('Seat','León');
```

### Método estático

Pueden ser llamados sin instanciar la clase

```javascript
class Vehiculo {
    
    constructor(marca, modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }
    
    static calculoITV(antiguedad){
        // Do stuff here
    }
    
};

const coche1 = new Vehiculo('Ford','Fiesta');

console.log(Vehiculo.calculoITV(5));
```

### Subclases

```javascript
class Vehiculo {
    
    constructor(marca, modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }
    
    /*
    También podría hacerse
    Vehiculo.prototype.calculoITV = function {
        // Do stuff here
        }
    */
    static calculoITV(antiguedad){
        // Do stuff here
    }
    
};

class Coche extends Vehiculo {
    calculoITV(antiguedad) {
        // Do stuff here
        /*
        También podria hacerse así para llamar al padre:
        super.calculoITV(antiguedad)
        */
    }
}
```

### Métodos y propiedades privadas

#### Symbol\(\)class Vehicle {

}

```javascript
const _color = Symbol();
const _comprador = Symbol();
```

```javascript
class Vehiculo {
    
    constructor(marca, modelo, color) {
        this.marca = marca;
        this.modelo = modelo;
        // Propiedad privada
        this[_color] = color;
    }

    // Método privado
    [_comprador]() {
        // Do stuff here
    }

    static calculoITV(antiguedad){
        // Do stuff here
    }
    
};
```

#### WeakMap\(\)

Implementar propiedades y métodos privados. Ofrecen mejor protección que Symbols. No hay posibilidad de acceder a miembros privados desde fuera del objeto.

```javascript
const _fechaFabricacion = new WeakMap();

class Vehiculo {
    
    constructor(marca, modelo, color, fechaFabricacion) {
        this.marca = marca;
        this.modelo = modelo;
        // Propiedad privada
        this[_color] = color;
        _fechaFabricacion.set(this,fechaFabricacion);
    }

    // Método privado
    [_comprador]() {
        // Do stuff here
    }
    
    getFechaFabricacion(){
        return _fechaFabricacion.get(this);
    }

    static calculoITV(antiguedad){
        // Do stuff here
    }
    
};


// Llamada
const v =  new Vehicle('Ford','Fiesta','rojo',2000);
console.log(_fechaFabricacion.get(v));
v.calculoITV(10);
```

## Módulos

### ES5 Sintaxis para módulos

* AMD \(Asynchronous module definition\) =&gt; Browser
* CommonJS =&gt; Nodejs
* UMD \(Universal Module Definition\) =&gt; Browser / nodejs

#### CommonJS modules

vehicle.js

```javascript
// Detalle de la implementación
const _fechaFabricacion = new WeakMap();

// Interfaz pública
class Vehiculo {
    
    constructor(marca, modelo, color, fechaFabricacion) {
        this.marca = marca;
        this.modelo = modelo;
        // Propiedad privada
        this[_color] = color;
        _fechaFabricacion.set(this,fechaFabricacion);
    }

    // Método privado
    [_comprador]() {
        // Do stuff here
    }
    
    getFechaFabricacion(){
        return _fechaFabricacion.get(this);
    }

    static calculoITV(antiguedad){
        // Do stuff here
    }
    
};

// Exporta el objeto. Para exportar más objetos, 
// solo hay que seguir el mismo procedimiento.

/*
Puede hacerse también así:
module.exports = Vehicle;
*/
module.exports.Vehicle = Vehicle;
module.exports.Car = Car;
```

index.js 

```javascript
const Vehicle = require('./vehicle');

const v =  new Vehicle('Ford','Fiesta','rojo',2000);
console.log(_fechaFabricacion.get(v));
v.calculoITV(10);
```

### ES6 Modules

Soporta nativamente los formatos de módulos

vehicle.js

```javascript
// Detalle de la implementación
const _fechaFabricacion = new WeakMap();

// Interfaz pública
class Vehiculo {
    
    constructor(marca, modelo, color, fechaFabricacion) {
        this.marca = marca;
        this.modelo = modelo;
        // Propiedad privada
        this[_color] = color;
        _fechaFabricacion.set(this,fechaFabricacion);
    }

    // Método privado
    [_comprador]() {
        // Do stuff here
    }
    
    getFechaFabricacion(){
        return _fechaFabricacion.get(this);
    }

    static calculoITV(antiguedad){
        // Do stuff here
    }
    
};
```

index.js 

```javascript
import {Vehicle} from './circle.js';

const v =  new Vehicle('Ford','Fiesta','rojo',2000);
console.log(_fechaFabricacion.get(v));
v.calculoITV(10);
```

index.html

```markup
<script type="module" src="index.js"></script>
```

## Frameworks

### Animación

#### [Animate.css](https://daneden.github.io/animate.css/)

#### [Bounce.js](http://bouncejs.com)

#### [Animejs](http://animejs.com)

