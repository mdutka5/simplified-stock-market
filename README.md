# Simplified Stock Market

A simplified stock market simulation REST API built with Spring Boot, PostgreSQL, and Docker.

## Architecture

The application runs as 3 Spring Boot instances behind an Nginx load balancer, providing high availability. If one instance is killed, the remaining instances continue to serve requests.

```
                                  |-> Spring Boot Instance 1
Client -> Nginx (load balancer) --|-> Spring Boot Instance 2
                                  |-> Spring Boot Instance 3
                                           |
                                      PostgreSQL
```

## Prerequisites

- Docker

## Starting the Application

```
PORT=8080 docker compose up -d
```

Where `PORT` is the port you want the application to be available on.

### First startup

First startup may take 2-3 minutes due to Docker image downloads and build. Subsequent startups take ~15 seconds.

### Verify everything is running

```
docker compose ps
```

### View logs

```
docker compose logs -f
```

### Stop the application

```
docker compose down
```

## API Endpoints

### Bank

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/stocks` | Returns current state of the bank |
| POST | `/stocks` | Sets the state of the bank |

#### GET /stocks response:
```
{
  "stocks": [
    {"name": "AAPL", "quantity": 420},
    {"name": "NVDA", "quantity": 69}
  ]
}
```

#### POST /stocks body:
```
{
  "stocks": [
    {"name": "AAPL", "quantity": 420},
    {"name": "NVDA", "quantity": 69}
  ]
}
```

### Wallets

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/wallets/{wallet_id}` | Returns current state of the wallet |
| GET | `/wallets/{wallet_id}/stocks/{stock_name}` | Returns quantity of specific stock in wallet |
| POST | `/wallets/{wallet_id}/stocks/{stock_name}` | Buy or sell a stock |

#### GET /wallets/{wallet_id} response:
```
{
  "id": "john123",
  "stocks": [
    {"name": "AAPL", "quantity": 5},
    {"name": "GOOGL", "quantity": 10}
  ]
}
```

#### GET /wallets/{wallet_id}/stocks/{stock_name} response:
```
99
```

#### POST /wallets/{wallet_id}/stocks/{stock_name} body:
```
{
  "type": "buy"
}
```

or

```
{
  "type": "sell"
}
```

### Audit Log

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/log` | Returns entire audit log in order of occurrence |

#### GET /log response:
```
{
  "log": [
    {"type": "buy", "walletId": "john123", "stockName": "AAPL"},
    {"type": "sell", "walletId": "jane456", "stockName": "GOOGL"}
  ]
}
```

### Chaos

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/chaos` | Kills the instance serving this request |

## Error Responses

| Status Code | Description |
|-------------|-------------|
| 200 | Operation successful |
| 400 | Bad request (e.g. no stock available) |
| 404 | Resource not found |
| 500 | Internal server error |

## High Availability

The application runs 3 instances behind Nginx. To test high availability:

```
# kill one instance
curl -X POST http://localhost:8080/chaos

# verify app still works
curl http://localhost:8080/stocks

# check which instance died
docker compose ps
```

## Testing

### Unit tests
```
./mvnw test
```

### All tests (requires Docker running)
```
./mvnw verify
```

## Tech Stack

- Java 21
- Spring Boot 3.4.5
- PostgreSQL 16
- Nginx
- Docker + Docker Compose
- JUnit 5
- TestContainers (integration tests)
- Mockito (unit tests)