# Starlink project
![Coverage](https://img.shields.io/badge/coverage-100%25-green) ![Issues](https://img.shields.io/badge/issues-3-orange) ![Java](https://img.shields.io/badge/language-Java-lightgrey) ![Java](https://img.shields.io/badge/language-Java-lightgrey) 

This in an innovation project that send probes to planets!

- [initial challenge](docs/challenge.md)

## Start application

This application use profile to separate environments.

### Develop

#### Requirements

- Maven
- MySQL (At the root of this project there is a docker compose file with MySQL)

#### Running

`mvn spring-boot:run -Dspring-boot.run.profiles=dev`

### Production

#### Requirements

- Docker compose

#### Running

At the root project

`docker-compose up -d`

### Running tests

`mvn test`

## Features

### User

- Create a user
- List all users

### Planet

- Create a planet
- List all planets
- Find a planet

### Probe

- Create a probe
- List all probes
- Find a probe
- Send probe to planet
- Send command to probe

## How to use

- Create a user
- Do login with this user and capture a jwt token on response
- Create a planet
- Create a probe
- Send probe to planet
- Send commands to probe

## Documentation

The api documentation for this project can be found in the path below

http://localhost:8080/swagger-ui/index.html

## Technologies

- Spring boot
- Spring auth with JWT
- Spring Data
- Docker and docker-compose
- Swagger
- Spring boot test with junit and mockito

### Roadmap

The next features are listeds on issues of this project https://github.com/HenryFarias/elo7-starlink/issues

# Contributing

To contribute feel free to open an issue or a pull request on this GitHub =D