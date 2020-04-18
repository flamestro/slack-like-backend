# SlackLikeBackendSpring

A project I made for University.
It's a SlackLike Backend written in Spring Boot 2.

Run with 
`docker-compose up --build`


Exposes:

- Spring Boot 2 Application on `8080`

- MySQL Database on `3306`

or start only the MySQL Service:
`docker-compose -f docker-compose.yml up db`

and then do `mvn spring-boot:run` to run the application on port `8080`