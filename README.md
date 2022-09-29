https://dev.azure.com/snnd/recipes/_apis/build/status/snncoding.recipes?branchName=main
# Favoruite Recipe Rest Api 

----------------------------
### Request examples
You can make sample queries with swagger or Postman using the following JSON calls.

URL : .../api/recipe/search 
METHOD : POST

----------------------------
- All vegetarian recipes
```JSON
{
  "isVegetarian": true
}
```

- Recipes that can serve 4 persons and have “potatoes” as an ingredient
```JSON
{
  "ingredients": [
    {
      "criteria": "potatoes",
      "included": true
    }
  ],
  "numberOfServing": 4
}
```

- Recipes without “salmon” as an ingredient that has “oven” in the instructions.
```JSON
{
  "ingredients": [
    {
      "criteria": "salmon",
      "included": false
    }
  ],
  "instructions": [
    {
      "criteria": "oven",
      "included": true
    }
  ]
}
```

----------------------------
### Features

----------------------------

- Unit test coverage is over 90%.
- Used Mockito
- Validation is applied
- Dynamic search created.
- Applied CI/CD
- Also applied auto deploy to heroku but does not succeed 

----------------------------
## Spring Boot

---------------------------
### Tech Info


- Java 11
- Spring Boot
- Spring Data JPA
- H2 In memory database
- JUnit 5
- Lombok
- Swagger
- Maven

### Maven
```ssh
 $ cd /recipe
 $ mvn clean install
 $ mvn spring-boot:run
```
Swagger UI will be run on this url
http://localhost:8080/swagger-ui.html
