# Java

## Conversión de datos

```java
// String a int
String numero = "5";
int n = Integer.getInt(numero);
// int a String
int a = 6;
String as = String.valueOf(a);
```

#### Casting

```java
String s = "hola";
renderRequest.setAttribute("cadena", s);

main.jsp
<%
String cadenaRecibida = (String) request.getAttribute("cadena");
%>
```

## Spring framework

Herramientas disponibles

* [Intellij IDEA Ultimate](https://spring.io/guides/gs/intellij-idea/)
* [Spring Tools Suite](https://spring.io/guides/gs/sts)
* [Eclipse](https://www.eclipse.org/community/eclipse_newsletter/2018/february/springboot.php)

Requisitos:

* JDK &gt;= 8
* Gradle 4+ o Maven 3.2+

### Construcción de servicio REST

#### Definir el modelo

{% code-tabs %}
{% code-tabs-item title="src/main/java/docs/Author.java" %}
```java
package docs;

public class Author {
	
	private final long id;
	private final String firstName;
	private final String lastName;
	
	public Author(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
}

```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Definir el controlador

Las peticiones HTTP son gestionadas por el controlador. Los componentes se identifican por **@RestController**, y **GreetingController** controla las peticiones **GET** de **/greeting** devolviendo un objeto **Greeting**.

{% code-tabs %}
{% code-tabs-item title="src/main/java/docs/AuthorController.java" %}
```java
package docs;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
	
	private final AtomicLong contador = new AtomicLong();
	
	@RequestMapping("/author")
	public Author author(@RequestParam(required=true) String firstName, @RequestParam(required=true) String lastName){
		return new Author(contador.incrementAndGet(), firstName, lastName);
	}
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

* **@RequestMapping** asegura que las peticiones HTTP a _/greeting_ estén mapeadas al método **greeting\(\)**. **@RequestMapping** mapea todas las operaciones HTTP por defecto. Si se desea especificar el tipo de operación: **@RequestMapping\(method=GET\)**
* **@RequestParam** Enlaza el valor de parametro de la consulta _name_ en el parámetro **name** del método **greeting\(\)**. En el caso de que el parámetro **name** no se encuentre presente, utilizará el valor por defecto **defaultValue**.

#### Hacer la aplicación ejecutable

{% code-tabs %}
{% code-tabs-item title="src/main/java/hello/Application.java" %}
```java
package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

* **@SpringBootApplication**
  * Añade las etiquetas **@Configuration** a las clases como definiciones del bean para el contexto de la aplicación.
  * **@EnableAutoConfiguration** le dice a Spring Boot empezar añadiendo beans basados en la configuración del classpath, beans y otras propiedades
  * **@EnableWebMvc** para aplicaciones Spring MVC. Esto activa comportamientos como _DispatcherServlet_.
  * **@ComponentScan** le dice a Spring que mire otros componentes, configuración y servicios en el paquete **hello**, con lo cual permite encontrar los controladores.
* **SpringApplication.run\(\)** es un método Spring Boot para iniciar la aplicación.

#### Construir un JAR ejecutable

* Con Gradle

```bash
# Iniciar la aplicación
$ ./gradlew bootRun
# Construir JAR
$ ./gradlew build
# Ejecutar JAR
$ java -jar build/libs/gs-rest-service-0.1.0.jar
```

* Con Maven

```bash
# Iniciar la aplicación
$ ./mvnw spring-boot:run
# Construir JAR
$ ./mvnw clean package
# Ejecutar JAR
$ java -jar target/gs-rest-service-0.1.0.jar

```

#### Ejecución del servicio

Visitar:  **http://localhost:8080/greeting**

Respuesta:

```javascript
Author {id=1, firstName='Reyes', lastName='Monforte'}
```

### Consumición de servicio REST

#### Definir el modelo

{% code-tabs %}
{% code-tabs-item title="src/main/java/hello/Author.java" %}
```java
package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Author {
	
	private long id;
	private String firstName;
	private String lastName;
	
	public Author() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
	}

}

```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Definir la aplicación

{% code-tabs %}
{% code-tabs-item title="src/main/java/hello/Application.java" %}
```java
package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Author author = restTemplate.getForObject(
					"http://localhost:8080/author?firstName=aa&lastName=bb", Author.class);
			log.info(author.toString());
		};
	}
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

#### Construir un JAR ejecutable

* Con Gradle

```bash
# Iniciar la aplicación
$ ./gradlew bootRun
# Construir JAR
$ ./gradlew build
# Ejecutar JAR
$ java -jar build/libs/gs-rest-service-0.1.0.jar
```

* Con Maven

```bash
# Iniciar la aplicación
$ ./mvnw spring-boot:run
# Construir JAR
$ ./mvnw clean package
# Ejecutar JAR
$ java -jar target/gs-rest-service-0.1.0.jar
```

#### Resultado de la ejecución

```bash
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.3.RELEASE)

2018-07-16 12:19:08.942  INFO 20275 --- [           main] hello.Application                        : Starting Application v0.1.0 on PR252 with PID 20275 (/home/zrubio/LEARN/workspace/gs-consuming-rest-initial/target/gs-consuming-rest-0.1.0.jar started by zrubio in /home/zrubio/LEARN/workspace/gs-consuming-rest-initial)
2018-07-16 12:19:08.945  INFO 20275 --- [           main] hello.Application                        : No active profile set, falling back to default profiles: default
2018-07-16 12:19:08.979  INFO 20275 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@5479e3f: startup date [Mon Jul 16 12:19:08 CEST 2018]; root of context hierarchy
2018-07-16 12:19:09.596  INFO 20275 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-07-16 12:19:09.610  INFO 20275 --- [           main] hello.Application                        : Started Application in 0.892 seconds (JVM running for 1.19)
2018-07-16 12:19:09.708  INFO 20275 --- [           main] hello.Application                        : Author{id=5, firstName='aa', lastName='bb'}
2018-07-16 12:19:09.709  INFO 20275 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@5479e3f: startup date [Mon Jul 16 12:19:08 CEST 2018]; root of context hierarchy
2018-07-16 12:19:09.711  INFO 20275 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown

```

### Accediendo a los datos con JPA \(Java Persistence API\)

#### Definir la entidad

{% code-tabs %}
{% code-tabs-item title="src/main/java/hello/Author.java" %}
```java
package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
/* @Entity(name="Author")*/
/* @Table(name="autores")*/
public class Author {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	
	protected Author() {}

	public Author(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format(
                "Author[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
	}
	
}

```
{% endcode-tabs-item %}
{% endcode-tabs %}

* Tiene 2 constructores:
  * Protected -&gt; Es el constructor por defecto para JPA. No se utiliza directamente, por eso está como protected.
  * Public -&gt; Es con el que se crean las instancias a Author para guardar en la base de datos.

Etiquetado:

* @Entity Indica que es una entidad JPA y con el que se realizarán las consultas HQL.
* @Table Indicar la tabla en la que se almacenarán los datos. 
* @Id ID del objeto
* @GeneratedValue El ID se genera automáticamente.





