## Angular 4 + SpringRest

A simple angular web app. Webservice with spring-rest that provides model data (that would be loaded from the database).  
The webservice can also be called from any device, it avoids duplicated codes for loading data and security issues

## Oauth2 and Spring

`Gradle` used as building tool, with latest release dependencies.  
The app is totally `java configured` (no included xml) `without spring-boot`.  
It provides a `custom AuthenticationProvider` to be able to perform yourself
the connection check with a password (alternative of UserDetailsService that
force to load an user from its username without password).

`Spring-data-jpa` installed and configured with `SqlServer` (easy to change by the configuration file).

## Installation

To install the rest, create a gradle module for the directory `clktime-rest`.  
Then rename `.properties.dist` to `.properties`.  
Refresh dependencies, and create a `TomcatLocal` running configuration defining the app war.  
I recommend Intellij.