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

## Asset Framework \(Framework de contenidos\)

Permite añadir al núcleo Liferay nuevas prestaciones. Por ejemplo, crear una aplicación para gestionar eventos, con lo que el framework permite añadir a los usuarios etiquetas, categorías o comentarios.

Asset es cualquier contenido del portal: texto, fichero, url, imagen, documentos, entradas de blog, marcadores, wiki, ...

**Añadir / actualizar y eliminar contenidos para las entidades personalizadas**

Para poder invocar un método del Asset Framework, debemos asociar AssetEntry con la entidad que nosotros queramos.

Debe implementarse indexadores a las entidades del portlet. 

#### Requisitos

{% code-tabs %}
{% code-tabs-item title="service.xml" %}
```markup
<reference package-path="com.liferay.portlet.asset" entity="AssetEntry" />
</entity>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Añadir y actualizar assets

{% hint style="info" %}
\***LocalServiceImpl** hereda de **AssetEntryLocalService**. Ésta esta asignada a la variable **assetEntryLocalService**.
{% endhint %}

Para añadir entidades personalizadas como un asset de Liferay, debe invocarse **assetEntryLocalService.updateEntry\(\)**

{% code-tabs %}
{% code-tabs-item title="AssetEntryLocalService.java" %}
```java
AssetEntry updateEntry(
    long userId, long groupId, Date createDate, Date modifiedDate,
    String className, long classPK, String classUuid, long classTypeId,
    long[] categoryIds, String[] tagNames, boolean listable,
    boolean visible, Date startDate, Date endDate, Date publishDate,
    Date expirationDate, String mimeType, String title,
    String description, String summary, String url, String layoutUuid,
    int height, int width, Double priority)
throws PortalException
```
{% endcode-tabs-item %}
{% endcode-tabs %}

Después de invocar updateEntry\(\), debe actualizarse el asset y el índice de la entidad.

#### Eliminar assets

Para eliminar entidades personalizadas como un asset de Liferay, debe invocarse **assetEntryLocalService.deleteEntry\(\)**

{% code-tabs %}
{% code-tabs-item title="\*LocalServiceImpl.java" %}
```java
assetEntryLocalService.deleteEntry(
    ENTITY.class.getName(), ENTITY.getInsultId());

Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(ENTITY.class);
indexer.delete(ENTITY);
```
{% endcode-tabs-item %}
{% endcode-tabs %}

Para que el **Asset Publisher de Liferay** muestre la entidad, la entidad debe tener un **Asset Renderer**. 

### Implementar Asset Categories y tags

Permite a los autores especificar tags y categorias para las entidades en la Vista. JSP Tags de Liferay permiten mostrar las categorias y las etiquetas.

{% code-tabs %}
{% code-tabs-item title="edit\_entry.jsp" %}
```java
<%--
Muestra mensajes de exito / error en en input de categorias y etiquetas
--%>
<liferay-ui:asset-categories-error />
<liferay-ui:asset-tags-error />
...
<aui:fieldset-group markupView="lexicon">
    
    <%--
    Permite a los usuarios mostrar/ocultar las opciones de introducción de categorias/tags
    --%>
    <aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
        <%--
         Permite a los usuario buscar o seleccionar las categorias
         Permite crear nuevo etiquetado ...
        --%> 
        <aui:input name="categories" type="assetCategories" />

        <aui:input name="tags" type="assetTags" />
    </aui:fieldset>
    ...
</aui:fieldset-group>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Mostrar categorias

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<p><liferay-ui:message key="categories" />:</p>

<div class="entry-categories">
    <liferay-ui:asset-categories-summary
        className="<%= BlogsEntry.class.getName() %>"
        classPK="<%= entry.getEntryId() %>"
        portletURL="<%= renderResponse.createRenderURL() %>"
    />
</div>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Mostrar etiquetas

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<div class="entry-tags">
    <p><liferay-ui:message key="tags" />:</p>

    <liferay-ui:asset-tags-summary
        className="<%= BlogsEntry.class.getName() %>"
        classPK="<%= entry.getEntryId() %>"
        portletURL="<%= renderResponse.createRenderURL() %>"
    />
</div>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% hint style="info" %}
**portletURL** permite la navegación entre categorias y etiquetas. 
{% endhint %}

{% hint style="info" %}
Cada _etiqueta_ que utiliza **portletURL** se convierte en un enlace que contiene la **portletURL** con la **etiqueta** o **categoriaId**.
{% endhint %}

### Relacionar assets \(AssetLink\)

Contenido relacionado.

#### Requisitos

{% code-tabs %}
{% code-tabs-item title="service.xml" %}
```markup
<reference package-path="com.liferay.portlet.asset" entity="AssetLink" />
</entity>
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Añadir y actualizar contenido relacionado {#anadir-y-actualizar-assets}

{% code-tabs %}
{% code-tabs-item title="AssetLinkLocalService" %}
```java
assetLinkLocalService.updateLinks(
    userId, assetEntry.getEntryId(), assetLinkEntryIds,
    AssetLinkConstants.TYPE_RELATED);
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Eliminar AssetLinks

{% code-tabs %}
{% code-tabs-item title="\*LocalServiceImpl.java" %}
```java
AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
    ENTITY.class.getName(), ENTITYId);

assetLinkLocalService.deleteLinks(assetEntry.getEntryId());
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Relacional AssetLinks en la JSP

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
    <liferay-ui:input-asset-links
        className="<%= BlogsEntry.class.getName() %>"
        classPK="<%= entryId %>"
    />
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Mostrar contenido relacionado

{% code-tabs %}
{% code-tabs-item title="view.jsp" %}
```java
<%
long insultId = ParamUtil.getLong(renderRequest, "insultId");
Insult ins = InsultLocalServiceUtil.getInsult(insultId);
AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Insult.class.getName(), ins.getInsultId());
%>


<liferay-ui:asset-links
    assetEntryId="<%=(assetEntry != null) ? assetEntry.getEntryId() : 0%>"
    className="<%=Insult.class.getName()%>"
    classPK="<%=ins.getInsultId()%>" />
```
{% endcode-tabs-item %}
{% endcode-tabs %}



