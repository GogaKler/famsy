.PHONY: dev-backend dev-frontend dev-postgres dev-all stop-backend stop-frontend stop-postgres stop-all restart-backend restart-frontend restart-all logs-backend logs-frontend logs-postgres

dev-backend:
	docker compose -f backend/docker-compose.yml up -d --build

dev-frontend:
	docker compose -f frontend/docker/development/docker-compose.yml up -d --build

dev-postgres:
	docker compose -f postgres/docker-compose.yml up -d

dev-all: dev-postgres dev-backend dev-frontend
	@echo "Запущены все сервисы в режиме разработки"

##### STOP #####
stop-backend:
	docker-compose -f backend/docker-compose.yml down

stop-frontend:
	docker compose -f frontend/docker/development/docker-compose.yml down

stop-postgres:
	docker-compose -f postgres/docker-compose.yml down

stop-all: stop-backend stop-frontend stop-postgres
	@echo "Все сервисы остановлены"

##### RESTART #####
restart-backend: stop-backend dev-backend
	@echo "Backend сервис перезапущен"

restart-frontend: stop-frontend dev-frontend
	@echo "Frontend сервис перезапущен"

restart-all: stop-all dev-all
	@echo "Все сервисы перезапущены"

##### Logs #####
logs-backend:
	docker-compose -f backend/docker-compose.yml logs -f

logs-frontend:
	docker-compose -f frontend/docker/development/docker-compose.yml logs -f

logs-postgres:
	docker-compose -f postgres/docker-compose.yml logs -f

