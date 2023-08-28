# java-explore-with-me
Template repository for ExploreWithMe project.
https://github.com/moongrail/java-explore-with-me/pull/5
### Описание:
Приложение — афиша. В этой афише можно предложить какое-либо событие от выставки до похода
в кино и собрать компанию для участия в нём.
#### Два сервиса
1) Основной сервис содержит всё необходимое для работы продукта;

2) Сервис статистики хранит количество просмотров и позволяет делать различные выборки для анализа 
работы приложения.

### Схема DB основного приложения.

### Спецификации API приложения:
1) Содержит api для главного сервиса [ewm-main-service-spec.json](ewm-main-service-spec.json) 
2) Содержит api для сервиса статистики [ewm-stats-service-spec.json](ewm-stats-service-spec.json)

### Запуск:
docker-compose up

### Стек технологий:
1) Java 11
2) Spring Boot
3) Spring Data
4) Spring Validation
5) Postgresql
6) Hibernate
7) Docker
8) Lombok