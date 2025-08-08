# Toolkit
A website with multiple subservices as java/spring-boot practice, currently only including parts of a Poll System

# Goals:
- [ ] Fully Implement Backend for Polls and Choices, maybe statistics for the votes of each Choice
- [ ] Proper Exception Handling
- [ ] Communicating with an external API (Currency Exchance)
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
- `psql -U root` (login)
- `CREATE DATABASE toolkit;` (Create DB, now you can run `docker compose down` and can run the App normally. After DB
   is created, the makefile command `make login` also works to conveniently log into db as root)

