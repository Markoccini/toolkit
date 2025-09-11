# Toolkit
Small Project for the sake of learning Java & Spring-Boot
A website with multiple subservices as java/spring-boot practice, currently only including parts of a Poll System

# Goals:
- [x] Fully Implement Backend for Polls and Choices, maybe statistics for the votes of each Choice
- [ ] Fleshed out Exception Handling
- [ ] Communicating with an external API? (Currency Exchange)
- [ ] Build a Frontend using Vue.js, Vite & Tailwind CSS
- [ ] Maybe a Login?
# Useful commands
- get into db-container: `docker exec -it postgres-toolkit bash`
- get into root: `psql -U root`
- create database: `CREATE DATABASE toolkit;`
- get into database: `\connect toolkit`
- create schema: `CREATE SCHEMA <schema-name>`
- See table overview: `\dt polls.*`

# Resetting the Database
- `docker compose down -v` (removes volumes so that no data can be reused when rebuilding the container)
- `docker compose up -d` (Rebuild Container)
- `docker exec -it postgres-toolkit bash` (Get into Container Shell)
- `psql -U root -d toolkit` (login)
- `CREATE SCHEMA polls;` (Creates schema polls in db, needs to be run once after first initializing the project)
