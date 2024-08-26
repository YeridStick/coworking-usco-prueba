# Coworking-USCO Microservices Project

Este proyecto implementa una arquitectura de microservicios para un sistema de gestión de espacios de coworking, utilizando Spring Boot y Spring Cloud.

## Estructura del Proyecto

El proyecto está compuesto por los siguientes microservicios:

- **authService**: Servicio de autenticación
- **userService**: Gestión de usuarios
- **reservaService**: Manejo de reservas
- **emailService**: Envío de correos electrónicos
- **balanceadorCarga**: Balanceador de carga
- **apiGateway**: Puerta de enlace API
- **eurekaServer**: Servidor de descubrimiento de servicios

## Requisitos Previos

- Java 17
- Maven
- Docker y Docker Compose

## Configuración y Ejecución

1. **Levantar servicios de infraestructura**

   Ejecute en la raíz del proyecto:

   ```
   docker-compose up -d
   ```

2. **Compilar el proyecto**

   ```
   mvn clean install
   ```

3. **Iniciar los microservicios**

   Inicie los servicios en este orden:

   1. eurekaServer
   2. apiGateway
   3. Los demás servicios en cualquier orden

   Para cada servicio:

   ```
   mvn spring-boot:run
   ```

## Puertos de los Servicios

- **Puertos Fijos:**
  - Eureka Server: 8761
  - API Gateway: 4040

- **Puertos Dinámicos:**
  - Todos los demás servicios utilizan puertos asignados dinámicamente.

Para conocer los puertos de los servicios con asignación dinámica:

1. Acceda a la interfaz web de Eureka: `http://localhost:8761`
2. Busque el servicio deseado en la lista de instancias registradas.
3. El puerto asignado se mostrará junto a la dirección IP del servicio.

Alternativamente, puede consultar los logs de cada servicio al iniciar, donde se imprimirá el puerto asignado.

## Acceso a las Herramientas

- Kafka UI: http://localhost:8080
- pgAdmin: http://localhost:5050
  - Email: admin@coworking.com
  - Password: admin_password

## Credenciales de Administrador

Para el acceso inicial al sistema:

- Número de Identificación: 00000001
- Nombre: Administrador
- Correo: [correo del administrador]
- Contraseña: [contraseña del administrador]

**Nota de Seguridad:** Cambie estas credenciales después del primer inicio de sesión.

## Gestión Segura de Credenciales

- Evite almacenar información sensible en el código fuente o archivos de configuración versionados.
- Use variables de entorno o servicios de gestión de secretos para información sensible en producción.
- Considere herramientas como Vault de HashiCorp para gestión segura de secretos en producción.

## Desarrollo

Para añadir nuevos microservicios, cree un nuevo módulo en el `pom.xml` raíz y configure las dependencias necesarias.

## Notas Adicionales

- Verifique que los servicios se registren correctamente en Eureka antes de usarlos.
- Ajuste la configuración de los servicios en `application.properties` o `application.yml` según sea necesario.
- Actualice y rote regularmente las credenciales de administrador y otras cuentas privilegiadas.
- La asignación dinámica de puertos facilita el despliegue y escalado de servicios, pero asegúrese de que su configuración de red y firewall permita esta flexibilidad.
