# Adidas Coding Challenge

## Travelling salesman problem

"Find the best route between two cities". The common sense would be to calculate every possible route and then choose the best one. The problem is, the more the cities the more exponential the calculation gets. So after, let's say, twenty cities you'll have heavy computation power and time to get the results. So, a bunch of algorithms is used nowadays in order to find not the best, but the closest to the best. So, to create it from scratch, I chosse the randomization of the routes. The number of cities is 50. At first, I ran for example 1000 combinations of routes, after every try to see if it is better than the earlier, I did permutations on the list. Then, to get better results, I split the route into many parts, and applied the same approach. Is interesting to say that after 10000 the results improve very little.

## Tecnologies


* Netflix Zuul for service discovering
* Netflix Eureka for service registry, load balancing and failover
* Sprinfox and Swagger for api documentation
* Postgresql 
* Docker for database containerization
* Spring securty, using io.jsonwebtoken for JWT management
* Spring boot for über application spinning

## Running 
1. make install
2. make run-primary
3. make run-second
4. make run-last

## Acessing
The database will have the initial data with 50 cities and the user "adidas:adidas"
To get the token: http://localhost:8080/gateway/authentication/v1/auth/signin

## Docs
http://localhost:8080/gateway/itinerary/swagger-ui.html
