.PHONY: reb login data

reb:
	docker kill toolkit-db && docker system prune -f && docker compose up -d
