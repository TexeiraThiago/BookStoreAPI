# Bookstore API

RESTful API de uma loja de livros construída em Java 21 utilizando Spring Boot 3.

## Tecnologias Utilizadas

- Java
- Spring Boot
- JPA (Jakarta Persistence API)
- Hibernate
- PostgreSQL
- Docker

## Diagrama de Classes

````mermaid
classDiagram
    class BookModel {
        -UUID id
        -String title
        -PublisherModel publisher
        -Set<BookModel> books
        -ReviewModel review
    }

    class PublisherModel {
        -UUID id
        -String name
        -Set<BookModel> books
    }

    class AuthorModel {
        -UUID id
        -String name
        -Set<BookModel> books
    }

    class ReviewModel {
        -UUID id
        -String reviewComment
        -BookModel book
    }

    BookModel --> PublisherModel: "1"
    BookModel --> "0..*" AuthorModel: "N"
    BookModel --> ReviewModel: "1"
    PublisherModel --> "0..*" BookModel: "N"
    AuthorModel --> "0..*" BookModel: "N"
````

## Como usar a API

A API da Bookstore permite gerenciar livros, autores, editoras e reviews. Veja abaixo os endpoints e como utilizá-los.

````json
{
  "title": "Nome do Livro",
  "publisherId": "UUID da Editora",
  "authorIds": [
    "UUID do Autor"
  ],
  "reviewComment": "Comentário sobre o Livro"
}
````

    Resposta: Retorna o livro criado com 201 Created.

### GET - Obter um Livro pelo ID

````html
http://localhost:8080/bookstore/books/{id}
````   

- Parâmetros:
  {id}: UUID do livro que você deseja buscar.

- Resposta: Retorna os dados do livro ou 404 Not Found se não for encontrado.

### GETALL - Listar todos os Livros

```htm
    GET http://localhost:8080/bookstore/books
```

- Resposta: Retorna uma lista com todos os livros cadastrados.

### PUT - Atualizar um Livro

````html
    http://localhost:8080/bookstore/books/{id}
````

- Parâmetros: 
  - {id}: UUID do livro que você deseja atualizar.
- Corpo da requisição:

````json
{
  "title": "Nome do Livro",
  "publisherId": "UUID da Editora",
  "authorIds": [
    "UUID do Autor"
  ],
  "reviewComment": "Comentário sobre o Livro"
}
````

- Resposta: Retorna o livro atualizado ou 404 Not Found se o livro não existir.

### DELETE - Remover um Livro
````html
    http://localhost:8080/bookstore/books/{id}
````

- Parâmetros:
  - {id}: UUID do livro que você deseja deletar.
- Resposta: Retorna 204 No Content em caso de sucesso, ou 404 Not Found se o livro não for encontrado.