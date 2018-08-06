# Liferay 7 & npm-liferay-bundler

1. Instala el paquete con npm
2. Añadir el paquete en el proceso build de npm

## Añadir npm-liferay-bundler

### Requisitos

* node.js &gt;= 6.11.0
* npm -&gt; Gestor de paquetes de nodejs
* PortletMVC

### Procedimiento

Ir a la carpeta resources: src/main/resources/META-INF/resources del portlet

Instalar el paquete liferay-npm-bundler

```bash
$ [sudo] npm install --save-dev liferay-npm-bundler
```

{% hint style="info" %}
En el caso de que dé problemas en localizar el package.json, utilizar npm init --yes, para crearlo por defecto. Más info: [https://docs.npmjs.com/getting-started/using-a-package.json](https://docs.npmjs.com/getting-started/using-a-package.json)
{% endhint %}

### Añadir el paquete en el build process

Editar package.json

{% code-tabs %}
{% code-tabs-item title="package.json" %}
```javascript
"scripts": {
      /* Añadir esta línea */
      "build": "[... && ] liferay-npm-bundler"
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% hint style="info" %}
\[... && \] hace referencia a cualquier proceso previo que debe ejecutarse
{% endhint %}

Puede utilizarse cualquier lenguaje mientras pueda ser traducido a ECMAScript 5 o mayor. El único requisito es que Babel pueda procesarlo.

## Configuración de npm-liferay-bundler

