package com.andre.adidas.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.andre.adidas.codechallenge")
public class ItinerarieCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItinerarieCalculatorApplication.class, args);
	}

	//String json = "[{\"name\":\"Mertzfurt\",\"x\":3,\"y\":11},{\"name\":\"East Carrolshire\",\"x\":4,\"y\":4},{\"name\":\"Simonisbury\",\"x\":11,\"y\":9},{\"name\":\"Lake Wendellstad\",\"x\":2,\"y\":6},{\"name\":\"Runteview\",\"x\":12,\"y\":7},{\"name\":\"Gearldineshire\",\"x\":5,\"y\":12},{\"name\":\"Jodyberg\",\"x\":9,\"y\":15},{\"name\":\"South Ismaelburgh\",\"x\":8,\"y\":9},{\"name\":\"Port Alfonso\",\"x\":12,\"y\":14},{\"name\":\"Rosemaryberg\",\"x\":6,\"y\":12},{\"name\":\"Pfefferside\",\"x\":15,\"y\":15},{\"name\":\"North Joshuaville\",\"x\":9,\"y\":12},{\"name\":\"Deangeloton\",\"x\":2,\"y\":1},{\"name\":\"Camillahaven\",\"x\":5,\"y\":9},{\"name\":\"Grimesland\",\"x\":9,\"y\":2},{\"name\":\"Darceyport\",\"x\":6,\"y\":9},{\"name\":\"Sterlingburgh\",\"x\":7,\"y\":5},{\"name\":\"McLaughlinbury\",\"x\":7,\"y\":7},{\"name\":\"Sulemaville\",\"x\":2,\"y\":3},{\"name\":\"Yundtmouth\",\"x\":11,\"y\":13},{\"name\":\"Bergetown\",\"x\":10,\"y\":6},{\"name\":\"South Scottyshire\",\"x\":14,\"y\":9},{\"name\":\"Shanefurt\",\"x\":10,\"y\":6},{\"name\":\"New Mel\",\"x\":1,\"y\":10},{\"name\":\"Cronaborough\",\"x\":1,\"y\":14},{\"name\":\"South Dyan\",\"x\":12,\"y\":1},{\"name\":\"Kovacekside\",\"x\":4,\"y\":6},{\"name\":\"New Owenburgh\",\"x\":6,\"y\":13},{\"name\":\"Franeckibury\",\"x\":9,\"y\":3},{\"name\":\"Port Davis\",\"x\":5,\"y\":14},{\"name\":\"Port Koriville\",\"x\":1,\"y\":6},{\"name\":\"Schusterchester\",\"x\":3,\"y\":15},{\"name\":\"North Lennie\",\"x\":1,\"y\":1},{\"name\":\"Cruickshankland\",\"x\":10,\"y\":1},{\"name\":\"Eleanorfurt\",\"x\":1,\"y\":10},{\"name\":\"Frankfort\",\"x\":2,\"y\":13},{\"name\":\"Rodville\",\"x\":9,\"y\":10},{\"name\":\"Lake Dylanview\",\"x\":7,\"y\":1},{\"name\":\"Pamouth\",\"x\":6,\"y\":3},{\"name\":\"South Anikatown\",\"x\":6,\"y\":10},{\"name\":\"Port Noriko\",\"x\":6,\"y\":1},{\"name\":\"North Olene\",\"x\":9,\"y\":15},{\"name\":\"Trantowland\",\"x\":15,\"y\":5},{\"name\":\"East Hershel\",\"x\":3,\"y\":3},{\"name\":\"Darrenhaven\",\"x\":5,\"y\":8},{\"name\":\"Port Isishaven\",\"x\":15,\"y\":7},{\"name\":\"East Winterside\",\"x\":11,\"y\":11},{\"name\":\"Hilpertside\",\"x\":10,\"y\":15},{\"name\":\"South Kathlinetown\",\"x\":8,\"y\":3},{\"name\":\"Gerholdchester\",\"x\":9,\"y\":14}]";
//	private static List<City> makeData() {
//		int size = 50;
//		List<City> cities = new ArrayList<>(size);
//				
//		Faker faker = Faker.instance();
//		for (int i = 0; i < size; i++) {
//			String name = faker.address().city();
//			Integer x = faker.random().nextInt(1, 15);
//			Integer y = faker.random().nextInt(1, 15);
//			
//			cities.add(new City(name, x, y));
//		}
//		
//		return cities;
//	}
//	
//	private static class ListTypeReference extends TypeReference<List<City>> {};
}
