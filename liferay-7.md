# Liferay 7

##  Instalación

* Introducir el nombre del portal
* Escoger idioma del portal
* Seleccionar si queremos que Liferay se instale con datos de ejemplo
* Introducir nombre, email del administrador del portal. 
  * Por defecto: test@liferay.com
  * Contraseña: Test
* Escoger el driver de conexión a base de datos
* Introducir nombre de la base de datos del portal
* Introducir usuario y contraseña de la base de datos

Una vez se ha guardado la configuración con éxito, se guardará en el archivo:

/home/zrubio/WORK/Liferay/LR7GA5/Mio/desarrollo/learning/bundles/**portal-setup-wizard.properties**

Cuando se reinicia el servidor, se crearán las tablas de la base de datos.

```bash
    __    ____________________  _____  __
   / /   /  _/ ____/ ____/ __ \/   \ \/ /
  / /    / // /_  / __/ / /_/ / /| |\  /
 / /____/ // __/ / /___/ _, _/ ___ |/ /
/_____/___/_/   /_____/_/ |_/_/  |_/_/

Starting Liferay Community Edition Portal 7.0.4 GA5 (Wilberforce / Build 7004 / October 23, 2017)

07:18:33,459 INFO  [localhost-startStop-1][StartupAction:118] There are no patches installed
07:18:33,538 WARN  [localhost-startStop-1][ReleaseLocalServiceImpl:229] Table 'learning.Release_' doesn't exist
07:18:33,541 INFO  [localhost-startStop-1][ReleaseLocalServiceImpl:126] Create tables and populate with default data
```

## Desarrollo de módulos

### Módulos OSGI

El contenedor OSGI en Liferay puede ejecutar cualquier módulo OSGi. 

* Cada módulo es empaquetado como un JAR. 
* Contiene el archivo MANIFEST para que el contenedor reconozca el módulo. 

Procedimiento:

1. **New -&gt; Liferay Module Project**
2. **Project name**. proyecto-web
3. **Build**. Gradle
4. **Project template**. mvc-portlet
5. **Component class name**: Proyecto
6. **Package name**: com.zrubio.proyecto.portlet

Gradle descarga las dependencias del proyecto automáticamente mientras se crea el proyecto. Jerarquía de archivos y carpetas:

* proyecto-web
  * src/main/java
    * com.zrubio.proyecto.portlet
      * ProyectoPortlet.java
    * com.zrubio.proyecto.constants
      * ProyectoPortletKeys.java
  * src/main/resources
    * content
      * Language.properties
    * META-INF
      * resources
        * init.jsp
        * view.jsp
  * src/main/resources/META-INF/resources/
    * init.jsp
    * view.jsp

Los proyectos creados con Liferay Module Project son generados como Componentes. Si el módulo encapsula piezas de la aplicación, el componente es el objeto que contiene el núcleo de la funcionalidad.

El componente es un objeto gestionado por un framework de componentes o un contenedor. Se despliegan dentro de los módulos.

Componente Declaratives Services \(DS\). Se declara que un objeto es un componente, y se define algunos datos sobre el componente para que el contenedor sepa cómo gestionarlo.

### Desarrollo front-end

Importar en todos los JSP excepto en init.jsp, init.jsp que es donde se declaran todas las importaciones de librerías, clases, etc.

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<%@ include file="/init.jsp" %>
<p>
    <b><liferay-ui:message key="guestbook-web.caption"/></b>
    /* Language.properties key="guestbook-web.caption" */
</p>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% code-tabs %}
{% code-tabs-item title="init.jsp" %}
```java
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />
```
{% endcode-tabs-item %}
{% endcode-tabs %}

Librerías:

AlloyUI \(JS\) 

```java
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
```

#### Añadir botón

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<aui:button-row>
	<aui:button value="Enviar"></aui:button>
</aui:button-row>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Generar URLs del portlet

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<portlet:renderURL var="saveEntryURL">
    <portlet:param name="mvcPath" value="/edit_entry.jsp"></portlet:param>
</portlet:renderURL>

<aui:button-row>
	<aui:button value="Enviar" onClick="<%= saveEntryURL.toString() %>"></aui:button>
</aui:button-row>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Asignar acciones al portlet \(Portlet actions\)

* Render - El portlet se pinta así mismo, haciendo uso de JSP
* Action - Se ejecuta una vez, cuando el usuario ejecuta una acción del portlet \(búsqueda, añadir un registro, ...\) Después de su ejecución, pasa a la fase Render con el nuevo estado.

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<%@ include file="/init.jsp" %>

<portlet:renderURL var="addEntryURL">
    <portlet:param name="mvcPath" value="/edit_entry.jsp"></portlet:param>
</portlet:renderURL>

<aui:button-row>
    <aui:button onClick="<%= addEntryURL.toString() %>" value="Add Entry"></aui:button>
</aui:button-row>
```
{% endcode-tabs-item %}
{% endcode-tabs %}



{% code-tabs %}
{% code-tabs-item title="edit\_entry.jsp" %}
```java
<%@ include file="/init.jsp" %>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/view.jsp"></portlet:param>
</portlet:renderURL>

<aui:button-row>
    <aui:button onClick="<%= viewURL.toString() %>" value="Regresar"></aui:button>
</aui:button-row>

<portlet:actionURL name="addEntry" var="addEntryURL"></portlet:actionURL>

<aui:form action="<%= addEntryURL %>" name="<portlet:namespace />fm">

        <aui:fieldset>
            <aui:input name="name"></aui:input>
            <aui:input name="message"></aui:input>
        </aui:fieldset>

        <aui:button-row>
            <aui:button type="submit"></aui:button>
            <aui:button type="cancel" onClick="<%= viewURL.toString() %>"></aui:button>
        </aui:button-row>
</aui:form>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

### Desarrollo back-end

MVC es un patrón de diseño para aplicaciones web que divide la aplicación entre: modelo, vista, controlador.

La capa de persistencia y de servicio se añaden a estas. Para que la aplicación funcione, se utiliza el Portlet Properties para crear la capa de persistencia.

#### Service Builder \(Constructor de servicios\)

Herramienta de Liferay para definir modelos de objetos y mapeado de los modelos para la base de datos SQL. Se define en service.xml, el modelo, el servicio y la capa de persistencia. También puede crear servicios web.

Proceso de creación:

1. New -&gt; Liferay Module Project
2. Nombre del proyecto
3. Service builder
4. Nombre del paquete: com.zrubio.docs.proyecto

Se crean 2 módulos: API y servicios.

* proyecto
  * proyecto-api
  * proyecto-service
    * service.xml

El modelo está en '-api' y el servicio y persistencia en '-service'

#### Implementar los métodos del servicio

CRUD en ProyectoLocalServiceImpl y ProyectoServiceImpl



## BaseMVCActionCommand



