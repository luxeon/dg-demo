# Sorting service

## How to start

Build jar file using Maven:
```
mvn clean install
```

Start:
```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Sort numbers

URL:
```
http://localhost:9002/sort
```
HTTP Method: POST

Request content type: application/json

Request body fields:
- numbers - comma separated numbers to be sorted
- order - sorting order type (asc - ascending order, desc - descending order)

Request body example:
```
{
  "numbers": [3, 1, 5, 3, 6, 4],
  "order": "asc"
}
```
Response body example:
```
[
    6,
    5,
    4,
    3,
    3,
    1
]
```
Response HTTP codes:
- 200 - OK
- 400 - Bad Request (validation errors)
- 500 - Internal server error