# Projeto pessoal para criação de aplicação completa com Spring Framework


## Run project (Needs Java installed):

### Install Jabba and activate Java 17:

```powershell
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-Expression (
  Invoke-WebRequest https://github.com/shyiko/jabba/raw/master/install.ps1 -UseBasicParsing
).Content
jabba install openjdk@1.17.0
jabba use 
```
## Run Maven Wrapper to build the project:
``./mvnw clean package``
## Run the application:
``java -jar target/PROJETO_JAVA-0.0.1.jar``
