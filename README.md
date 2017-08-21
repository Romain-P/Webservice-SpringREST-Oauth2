## Webservice with Spring REST

A Webservice with spring-rest that provides database models.
The webservice can also be called from any authorized device and allowed user.

## Features and tools

 * **Gradle** - used as building tool with the latest release dependencies.
 * **Java Configuration** - fully java configured without xml, `without spring-boot`
 * **Swagger** - generates automatically your api with tests *(/swagger-ui.html)*
 * **SpringREST** - using rest controllers with jackson to generate automatically json objects from dtos
 * **SpringDATA** - spring data jpa for the persistence. It performs a nice code generation from interfaces
 * **Custom Auth** - custom authentication to perform a connection via another webservice (ldap in this project)
 * **CorsFilter** - configured to allow rest requests from anywhere.
 * **Oauth 2** - provides a security layer by access token
 * **ModelMapper** - used for conversions between entities/dto's. (Maps fields from multiples objects with recursion)

## Installation

Rename resource `.properties.dist` to `.properties`.  
Refresh dependencies, and create a `TomcatLocal` running configuration defining the app war.  
I recommend Intellij.