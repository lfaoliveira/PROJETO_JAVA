# Projeto pessoal para criação de aplicação completa com Spring Framework


## Run project (Needs Java installed):
``java ``



## Deeper look (for devs):
### Install Jabba and jEnv:

```powershell
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-Expression (
  Invoke-WebRequest https://github.com/shyiko/jabba/raw/master/install.ps1 -UseBasicParsing
).Content

jabba install openjdk@1.17.0
jabba install maven@3.9.9
## Or if you prefer to only temporarily set java version:
jabba use 
```

``./mvnw clean install``
