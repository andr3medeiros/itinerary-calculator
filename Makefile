install:
	mvn install -Dmaven.test.skip=true
	
run-primary:
	docker-compose up -d service-discovery

run-second:
	docker-compose up -d authentication

run-last:
	docker-compose up -d itinerary