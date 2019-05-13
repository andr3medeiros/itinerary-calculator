run-all:
	make run-database
	mvn spring-boot:run -pl service-discovery &
	mvn spring-boot:run -pl gateway-service &
	mvn spring-boot:run -pl authentication &
	mvn spring-boot:run -pl itinerary &
run-database:
	cd itinerary && docker-compose up -d