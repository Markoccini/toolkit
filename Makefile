.PHONY: reb login data

SQL_FILE := sql_statements.txt

reb:
	docker kill postgres-toolkit && docker system prune -f && docker compose up -d

login:
	docker exec -it postgres-toolkit bash -c "psql -U root -d toolkit"


data:
	docker exec -it postgres-toolkit bash -c "psql -U root -d toolkit -c 'SELECT * FROM polls.choices'"