# Microsserviço de Eventos

Este é um microsserviço para gerenciar eventos. Ele permite a criação, atualização, recuperação e exclusão de eventos.

## Endpoints

- `POST /events/create-event`: Cria um novo evento.
- `GET /events/get-event/{id}`: Recupera um evento pelo ID.
- `GET /events/get-all-events`: Recupera todos os eventos.
- `PUT /events/update-event/{id}`: Atualiza um evento pelo ID.
- `DELETE /events/delete-event/{id}`: Exclui um evento pelo ID, verificando se há tickets relacionados. Antes de excluir o evento, o microsserviço de eventos faz uma requisição ao microsserviço de tickets para verificar se existem tickets associados ao evento.
- `GET /events/get-all-events/sorted`: Recupera todos os eventos ordenados.

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
spring.data.mongodb.database=event_db