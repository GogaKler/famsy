.PHONY: dev-backend dev-frontend dev-postgres dev-minio dev-all stop-backend stop-frontend stop-postgres stop-minio stop-all restart-backend restart-frontend restart-all logs-backend logs-frontend logs-postgres logs-minio

dev-backend:
	export UID=$(id -u)
	export GID=$(id -g)
	docker compose -f backend/docker-compose.yml up -d --build

dev-frontend:
	docker compose -f frontend/docker/development/docker-compose.yml up -d --build

dev-postgres:
	docker compose -f postgres/docker-compose.yml up -d

dev-minio:
	docker compose -f minio/docker-compose.yml up -d

dev-all: dev-postgres dev-backend dev-frontend dev-minio
	@echo "Запущены все сервисы в режиме разработки"

##### STOP #####
stop-backend:
	docker compose -f backend/docker-compose.yml down

stop-frontend:
	docker compose -f frontend/docker/development/docker-compose.yml down

stop-postgres:
	docker compose -f postgres/docker-compose.yml down

stop-minio:
	docker compose -f minio/docker-compose.yml down

stop-all: stop-backend stop-frontend stop-postgres stop-minio
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
	docker compose -f backend/docker-compose.yml logs -f

logs-frontend:
	docker compose -f frontend/docker/development/docker-compose.yml logs -f

logs-postgres:
	docker compose -f postgres/docker-compose.yml logs -f

logs-minio:
	docker compose -f minio/docker-compose.yml logs -f

