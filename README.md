# Backend service layer for managing staff and clients rota

## Setup:

- Java 8
- JPA/Hibernate
- Maven v3.3
- Dropwizard - modules [core, hibernate, auth]
- Postgres SQL v9.5

Database:

- emilena

## Security:


There are 2 types of Roles

1. ADMIN
2. SYSTEM

#####ADMIN

1. Grants system wide access
2. This includes adding, deleting staff members
3. This includes adding, deleting clients
4. This includes setting up new users of the system with both ADMIN and SYSTEM roles
5. Access to updating appointments, and the calendar

#####SYSTEM

1. Grants read-only access
2. This includes read-only access to client information
3. This includes read-only access to appointments and the calendar

#####Database - Postgres

Some useful commands:

dropdb [dbname], createdb [dbname], psql [dbname]

To run a script against the DB: psql -d [dbname] -a -f [script location]

#####Docker

See emilena-api/Dockerfile

1. docker build -t emilena-api .
2. docker run -p 9090:9090 emilena-api

