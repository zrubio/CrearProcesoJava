# Liferay 6.2

## portal-setup-wizard.properties

Se crea cuando finaliza la instalación

{% code-tabs %}
{% code-tabs-item title="portal-setup-wizard.properties" %}
```java
/* Configuración del administrador */
admin.email.from.address=test@liferay.com
admin.email.from.name=Test Test
/* Configuración de la HOME del Liferay */
liferay.home=/liferay-portal // PATH del portal
/* Instalación asistida */
setup.wizard.enabled=false
```
{% endcode-tabs-item %}
{% endcode-tabs %}

## portal-ext.properties

{% code-tabs %}
{% code-tabs-item title="portal-ext.properties" %}
```java

/* COnfiguración de JNDI */
jdbc.default.jndi.name=jdjb/NombreDB
```
{% endcode-tabs-item %}
{% endcode-tabs %}

## Desarrollo

### Service Builder

### Definir service.xml

El formato se especifica en [liferay-service-builder\_6\_2\_0.dtd ](http://www.liferay.com/dtd/liferay-service-builder_6_2_0.dtd)

docroot/WEB-INF/src/service.xml

```markup
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE service-builder PUBLIC "-//Liferay//DTD Service Builder 6.2.0//EN" "http://www.liferay.com/dtd/liferay-service-builder_6_2_0.dtd">
<service-builder package-path="com.zrubio.docs.library">
	<author>zrubio</author>
	<namespace>LB</namespace>
	<entity name="Book" local-service="true">


		<!-- PK fields -->

		<column name="bookId" type="long" primary="true"></column>

		<!-- Group instance -->

		<column name="groupId" type="long"></column>

		<!-- Audit fields -->

		<column name="companyId" type="long"></column>
		<column name="userId" type="long"></column>
		<column name="userName" type="String"></column>
		<column name="createDate" type="Date"></column>
		<column name="modifiedDate" type="Date"></column>
        <column name="title" type="String"></column>
        <column name="authorId" type="long"></column>
        <column name="release" type="Date"></column>
        <column name="imgId" type="long"></column>
        <column name="description" type="String"></column>
        <column name="publisherId" type="long"></column>
        <column name="categoryId" type="long"></column>
        <column name="isbn" type="String"></column>
        <finder name="G_A" return-type="Collection">
            <finder-column name="groupId"></finder-column>
            <finder-column name="authorId"></finder-column>
        </finder>
        <finder name="G_P" return-type="Collection">
            <finder-column name="groupId"></finder-column>
            <finder-column name="publisherId"></finder-column>
        </finder>
        <finder name="G_C" return-type="Collection">
            <finder-column name="groupId"></finder-column>
            <finder-column name="categoryId"></finder-column>
        </finder>
        <finder name="GroupId" return-type="Collection">
            <finder-column name="groupId"></finder-column>
        </finder>
	</entity>
	<entity name="Author" local-service="true">

		<!-- PK fields -->

		<column name="authorId" type="long" primary="true"></column>

		<!-- Group instance -->

		<column name="groupId" type="long"></column>

		<!-- Audit fields -->

		<column name="companyId" type="long"></column>
		<column name="userId" type="long"></column>
		<column name="userName" type="String"></column>
		<column name="createDate" type="Date"></column>
		<column name="modifiedDate" type="Date"></column>
        <column name="firstName" type="String"></column>
        <column name="lastName" type="String"></column>
        <column name="imgId" type="long"></column>
        <column name="description" type="String"></column>
        <column name="birthDate" type="Date"></column>
        <finder name="GroupId" return-type="Collection">
            <finder-column name="groupId"></finder-column>
        </finder>
	</entity>
	<entity name="Publisher" local-service="true">

		<!-- PK fields -->

		<column name="publisherId" type="long" primary="true"></column>

		<!-- Group instance -->

		<column name="groupId" type="long"></column>

		<!-- Audit fields -->

		<column name="companyId" type="long"></column>
		<column name="userId" type="long"></column>
		<column name="userName" type="String"></column>
		<column name="createDate" type="Date"></column>
		<column name="modifiedDate" type="Date"></column>
        <column name="name" type="String"></column>
        <column name="imgId" type="long"></column>
        <column name="description" type="String"></column>
        <finder name="GroupId" return-type="Collection">
            <finder-column name="groupId"></finder-column>
        </finder>
	</entity>
	<entity name="Category" local-service="true">

		<!-- PK fields -->

		<column name="categoryId" type="long" primary="true"></column>

		<!-- Group instance -->

		<column name="groupId" type="long"></column>

		<!-- Audit fields -->

		<column name="companyId" type="long"></column>
		<column name="userId" type="long"></column>
		<column name="userName" type="String"></column>
		<column name="createDate" type="Date"></column>
		<column name="modifiedDate" type="Date"></column>
        <column name="name" type="String"></column>
        <finder name="GroupId" return-type="Collection">
            <finder-column name="groupId"></finder-column>
        </finder>
	</entity>
    <exceptions>
        <exception>BookId</exception>
        <exception>BookTitle</exception>
        <exception>BookImgId</exception>
        <exception>BookDescription</exception>
        <exception>BookIsbn</exception>
        <exception>AuthorId</exception>
        <exception>AuthorFirstName</exception>
        <exception>AuthorLastName</exception>
        <exception>AuthorImgId</exception>
        <exception>AuthorBirthDate</exception>
        <exception>AuthorDescription</exception>
        <exception>PublisherId</exception>
        <exception>PublisherName</exception>
        <exception>PublisherImgId</exception>
        <exception>PublisherDescription</exception>
        <exception>CategoryId</exception>
        <exception>CategoryName</exception>
        <exception>BookAuthorId</exception>
        <exception>BookPublisherId</exception>
        <exception>BookCategoryId</exception>
    </exceptions>
</service-builder>
```

```text

```

### Definir LocalServiceImpl

#### Crear

```java
@Override
	public Author addAuthor(
		long userId, String firstName, String lastName, long imgId,
		String description, Date birthDate, ServiceContext serviceContext)
		throws SystemException, PortalException {

		long companyId = serviceContext.getCompanyId();
		long groupId = serviceContext.getScopeGroupId();
		
		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(0, firstName, lastName, imgId, description, birthDate);

		long authorId = counterLocalService.increment();

		Author author = authorPersistence.create(authorId);

		author.setUserId(userId);
		author.setUserName(user.getFullName());
		author.setGroupId(serviceContext.getScopeGroupId());
		author.setCompanyId(serviceContext.getCompanyId());
		author.setCreateDate(serviceContext.getCreateDate(now));
		author.setModifiedDate(serviceContext.getModifiedDate(now));
		author.setExpandoBridgeAttributes(serviceContext);

		author.setFirstName(firstName);
		author.setLastName(lastName);
		author.setImgId(imgId);
		author.setDescription(description);
		author.setBirthDate(birthDate);

		resourceLocalService.addResources(companyId, groupId, userId, Author.class.getName(), authorId,false,true, true);
		
		authorPersistence.update(author);

		return author;
	}
```

#### Eliminar

```java
@Override
	public Author deleteAuthor(long authorId)
		throws PortalException, SystemException {

		Author author = authorPersistence.findByPrimaryKey(authorId);
		return this.deleteAuthor(author);
	}

	@Override
	public Author deleteAuthor(Author author)
		throws SystemException {

		return authorPersistence.remove(author);
	}
```

#### Actualizar

```java
@Override
	public Author updateAuthor(
		long userId, long authorId, String firstName, String lastName,
		long imgId, String description, Date birthDate,
		ServiceContext serviceContext)
		throws SystemException, PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(authorId, firstName, lastName, imgId, description, birthDate);

		Author author = authorPersistence.findByPrimaryKey(authorId);

		author.setUserId(userId);
		author.setUserName(user.getFullName());
		author.setGroupId(serviceContext.getScopeGroupId());
		author.setCompanyId(serviceContext.getCompanyId());
		author.setModifiedDate(serviceContext.getModifiedDate(now));
		author.setExpandoBridgeAttributes(serviceContext);

		author.setFirstName(firstName);
		author.setLastName(lastName);
		author.setImgId(imgId);
		author.setDescription(description);
		author.setBirthDate(birthDate);

		authorPersistence.update(author);

		return author;

	}

```

#### Consultar

```java
public List<Author> getAuthors(long groupId)
		throws SystemException, PortalException {

		return authorPersistence.findByGroupId(groupId);
	}

	public List<Author> getAuthors(long groupId, int start, int end)
		throws SystemException, PortalException {

		return authorPersistence.findByGroupId(groupId, start, end);
	}

	public List<Author> findAll()
		throws SystemException, PortalException {

		return authorPersistence.findAll();
	}

	public List<Author> findAll(int start, int end)
		throws SystemException, PortalException {

		return authorPersistence.findAll(start, end);
	}

	public Author findByPrimaryKey(long authorId)
		throws SystemException, PortalException {

		return authorPersistence.findByPrimaryKey(authorId);
	}
```

#### Validación

```java
private void validate(
		long authorId, String firstName, String lastName, long imgId,
		String description, Date birthDate)
		throws PortalException {

		if (Validator.isNull(firstName)) {
			throw new AuthorFirstNameException();
		}

		if (Validator.isNull(lastName)) {
			throw new AuthorLastNameException();
		}

		if (imgId < 0) {
			throw new AuthorImgIdException();
		}

		if (Validator.isNull(description)) {
			throw new AuthorDescriptionException();
		}

		if (Validator.isNull(birthDate)) {
			throw new AuthorBirthDateException();
		}

	}
```

### Definir ServiceImpl

#### Crear

```java
@Override
	public Author addAuthor(
		long userId, String firstName, String lastName, long imgId,
		String description, Date birthDate)
		throws SystemException, PortalException {

		validate(0, firstName, lastName, imgId, description, birthDate);

		authorLocalService.addAuthor(userId,firstName,lastName,imgId,description, birthDate);

		return author;
	}
```

#### Eliminar

```java

```

#### Actualizar

```java

```

#### Consultar

```java

```

#### Validación

```java

```

### Definir controlador

#### Guardar y actualizar

```java
public void saveCategory(ActionRequest request, ActionResponse response)
		throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Category.class.getName(), request);

		ThemeDisplay themeDisplay =
			(ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();

		Category category = null;
		long categoryId = ParamUtil.getLong(request, "categoryId");
		String name = ParamUtil.getString(request, "name");

		try {

			if (categoryId == 0) {
				category = CategoryLocalServiceUtil.addCategory(
					serviceContext.getUserId(), name, serviceContext);
			}
			else {
				category = CategoryLocalServiceUtil.updateCategory(
					serviceContext.getUserId(), categoryId, name,
					serviceContext);
			}

		}
		catch (Exception ex) {
			SessionErrors.add(request, ex.getClass().getName());
			response.setRenderParameter(
				"mvcPath", "/html/library/edit_category.jsp");
		}
	}
```

#### Eliminar

```java
public void deleteCategory(ActionRequest request, ActionResponse response)
		throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Category.class.getName(), request);

		long categoryId = ParamUtil.getLong(request, "categoryId");

		try {
			CategoryLocalServiceUtil.deleteCategory(categoryId);
			SessionMessages.add(request, "Categoría eliminada");
		}
		catch (Exception ex) {
			SessionErrors.add(request, ex.getClass().getName());
			response.setRenderParameter("mvcPath", "/html/library/view.jsp");
		}
	}
```

#### Render

```java
@Override
	public void render(RenderRequest request, RenderResponse response)
		throws PortletException, IOException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Category.class.getName(), request);

			long groupId = serviceContext.getScopeGroupId();

			long categoryId = ParamUtil.getLong(request, "categoryId");

			List<Category> categories =
				CategoryLocalServiceUtil.getCategories(groupId);

			if (categories.size() == 0) {
				CategoryLocalServiceUtil.addCategory(
					serviceContext.getUserId(), "Inicial", serviceContext);
			}

			request.setAttribute("categories", categories);
		}
		catch (Exception ex) {
			throw new PortletException(ex);
		}

		super.render(request, response);
	}
```

### Definir la vista

#### init.jsp

```java
<%@ page import="com.liferay.portlet.PortletURLUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="javax.portlet.PortletSession" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.WindowState" %>

<%@ page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>

<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.kernel.util.Validator"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.kernel.repository.model.FileEntry"%>

<%@ page import="com.liferay.portal.security.permission.ActionKeys"%>

<liferay-theme:defineObjects />
<portlet:defineObjects />
```

#### Obtener URL actual

```java
<%
PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);
String currentURL = PortletURLUtil.getCurrent(renderRequest, renderResponse).toString();
%>
```

#### &lt;liferay-theme:defineObjects /&gt;

Utilizar algunos objetos predefinidos en la JSP

* **User**. El usuario actual
* **Time zone**. Zona horaria actual
* **themeDisplay**. Objeto que contiene elementos como el usuario que ha iniciado sesión, información del logo, ...
* **theme**. Tema actual renderizado por el portal.
* **scopeGroupId**. 
* **realUser**. Objeto User de administrador
* **portletDisplay**. Propiedades del portlet
* **plid**. Cada página en Liferay tiene un ID \(Portal Layout ID\). 
* **permissionChecker**.
* **locale**
* **layoutTypePortlet**
* **layout**
* **contact**
* **company**
* **colorScheme**
* **account**

#### &lt;portlet:defineObjects /&gt;

Heredamos los objetos predefinidos en la JSP.

