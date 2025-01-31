# Microsserviço de Tickets

Este é um microsserviço para gerenciar tickets. Ele permite a criação, atualização, recuperação e cancelamento de tickets.

## Endpoints

- `POST /tickets/create-ticket`: Cria um novo ticket. Antes de criar o ticket, o microsserviço de tickets faz uma requisição ao microsserviço de eventos para verificar se o evento existe.
- `GET /tickets/get-ticket/{id}`: Recupera um ticket pelo ID.
- `PUT /tickets/update-ticket/{id}`: Atualiza um ticket pelo ID.
- `GET /tickets/get-ticket-by-cpf/{cpf}`: Recupera tickets pelo CPF.
- `GET /tickets/check-tickets-by-event/{eventId}`: Recupera tickets por ID do evento, retornando apenas tickets com status "completed".
- `DELETE /tickets/cancel-ticket/{ticketId}`: Cancela um ticket pelo ID.
- `DELETE /tickets/cancel-tickets/{cpf}`: Cancela todos os tickets pelo CPF.

## Dependências

- Spring Boot
- Spring Data MongoDB
- RabbitMQ
- OpenFeign

## Instalação

1. Clone o repositório.
2. Navegue até a pasta do projeto.
3. Execute o comando `mvn clean install`.
4. Inicie a aplicação com o comando `mvn spring-boot:run`.

## Configuração

### Configuração do MongoDB

Configure as propriedades do MongoDB no `application.properties`.

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=ticket_db