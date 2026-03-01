# Access Logger Middleware - Documentação

## Visão Geral

O middleware **AccessLogger** foi implementado para registrar automaticamente informações sobre cada requisição HTTP que chega ao servidor, incluindo:

- **IP Address**: Endereço IP do cliente
- **Country**: País de origem do IP
- **Browser Name**: Nome do navegador do usuário
- **Browser Version**: Versão do navegador
- **User Agent**: String completa do User Agent
- **Request Path**: Caminho da requisição
- **HTTP Method**: Método HTTP (GET, POST, etc.)
- **Status Code**: Código de resposta HTTP
- **Timestamp**: Data e hora da requisição

## Componentes Implementados

### 1. **AccessLogEntity** (`model/entity/AccessLogEntity.java`)
Entidade JPA que representa um log de acesso no banco de dados.

**Campos:**
- `ipAddress`: IP do cliente
- `country`: País (obtido via geolocation)
- `userAgent`: User Agent completo
- `browserName`: Nome do navegador extraído
- `browserVersion`: Versão do navegador extraída
- `requestPath`: URI da requisição
- `httpMethod`: Método HTTP
- `statusCode`: Código de resposta
- `timestamp`: Data/hora do acesso

### 2. **AccessLogRepository** (`repository/AccessLogRepository.java`)
Interface JPA Repository para persistir dados de acesso no banco.

### 3. **GeoLocationService** (`service/GeoLocationService.java`)
Serviço que obtém o país a partir do endereço IP.

**Funcionalidades:**
- Consulta API `ip-api.com` para obter informações de geolocalização
- Implementa cache em memória para IPs já consultados
- Detecta e identifica IPs locais
- Trata exceções graciosamente

### 4. **UserAgentParserService** (`service/UserAgentParserService.java`)
Serviço que extrai informações do navegador do User Agent.

**Suporta:**
- Chrome
- Firefox
- Safari
- Edge
- Opera
- Internet Explorer
- Brave

### 5. **AccessLogService** (`service/AccessLogService.java`)
Serviço central que coordena o logging de acessos.

**Funcionalidades:**
- Integra GeoLocationService e UserAgentParserService
- Extrai IP considerando proxies (X-Forwarded-For, etc.)
- Salva dados no banco de dados
- Trata erros de forma segura

**Método Principal:**
```java
public void logAccess(String ipAddress, String userAgent, String requestPath, String httpMethod, Integer statusCode){}
```

### 6. **AccessLogger** (`middleware/AccessLogger.java`)
Filtro Spring que intercepta todas as requisições HTTP.

**Como funciona:**
1. Intercepta requisição HTTP
2. Extrai informações de IP, User Agent, path, método
3. Permite que a requisição prossiga normalmente
4. Após a resposta, registra o acesso no banco de dados
5. Implementa logging de erros

## Como Funciona o Middleware

O `AccessLogger` estende `OncePerRequestFilter` do Spring e é registrado automaticamente como `@Component`. Ele garante que:

1. **Cada requisição é processada uma única vez** - O Spring garante isso com `OncePerRequestFilter`
2. **O processamento não afeta a resposta** - O logging ocorre após o `filterChain.doFilter()`
3. **IPs de clientes em proxy são detectados** - Suporta headers como `X-Forwarded-For`
4. **Erros são tratados graciosamente** - Não interrompe o fluxo da aplicação

## Fluxo de Execução

```
HTTP Request
    ↓
AccessLogger.doFilterInternal()
    ↓
  Extrai: IP, User Agent, Path, Method
    ↓
filterChain.doFilter() → Processamento Normal da Requisição
    ↓
HTTP Response
    ↓
Extrai: Status Code
    ↓
AccessLogService.logAccess()
    ↓
GeoLocationService.getCountryByIp()
    ↓
UserAgentParserService extracts Browser info
    ↓
Salva em AccessLogEntity no banco de dados
    ↓
Log no console
```

## Configuração no Banco de Dados

A tabela `access_logs` será criada automaticamente pelo Hibernate com a seguinte estrutura:

```sql
CREATE TABLE access_logs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ip_address VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    user_agent VARCHAR(255) NOT NULL,
    browser_name VARCHAR(255) NOT NULL,
    browser_version VARCHAR(255) NOT NULL,
    request_path VARCHAR(255) NOT NULL,
    http_method VARCHAR(255) NOT NULL,
    status_code INTEGER NOT NULL,
    timestamp DATETIME NOT NULL
);
```

## Exemplo de Log

Um log típico no console seria:

```
Access logged: IP=192.168.1.100, Country=Brazil, Browser=Chrome 120.0.0.0, Path=/search/movie, Method=GET
```

## Performance

- **Cache de Geolocation**: Evita múltiplas chamadas à API para o mesmo IP
- **Processamento Assíncrono**: O logging ocorre em segundo plano sem afetar a resposta
- **Tratamento de Erros**: Se o logging falhar, a requisição original continua processando normalmente

## Segurança

- **IP Detection**: Detecta IPs reais mesmo atrás de proxies
- **Local IP Exclusion**: Identifica e marca IPs locais como "Local"
- **Cache com Limite**: Implementar limite de cache se houver muitos IPs únicos
- **API Rate Limiting**: Considerar implementar rate limiting para a API de geolocation

## Próximas Melhorias

1. **Persistência Assíncrona**: Usar `@Async` para não bloquear a resposta
2. **Rate Limiting**: Implementar limite de requisições por IP
3. **Cache Distribuído**: Usar Redis para cache compartilhado entre instâncias
4. **Alertas**: Disparar alertas para padrões suspeitos
5. **Dashboard**: Criar dashboard para visualizar logs de acesso


