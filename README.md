##Angular 4 + SpringRest

A simple angular web app. Webservice with spring-rest that provides model data (that would be loaded from the database).  
The webservice can also be called from any device, it avoids duplicated codes for loading data and security issues

##Oauth and Spring

Gradle used as building tool, with latest release dependencies.  
The app is totally java configured (no included xml) without spring-boot.  
It overrides a custom AuthenticationService to be able to perform yourself
the connection check with a password (alternative of UserDetailsService that force to load an user from its username without password).