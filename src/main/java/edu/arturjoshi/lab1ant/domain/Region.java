package edu.arturjoshi.lab1ant.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class Region {
    private static final String SEPARATOR = " ";
    private final Random random = new Random();
    private List<City> cities;

    public Region(String cities, List<String> citiesData) {
        this.cities = Arrays.stream(cities.split(SEPARATOR))
                .map(Integer::parseInt)
                .map(City::new)
                .collect(Collectors.toList());
        citiesData
                .forEach(s -> {
                    int firstCity = Integer.parseInt(s.split(SEPARATOR)[0]);
                    int secondCity = Integer.parseInt(s.split(SEPARATOR)[1]);
                    int distance = Integer.parseInt(s.split(SEPARATOR)[2]);
                    int ferment = Integer.parseInt(s.split(SEPARATOR)[3]);

                    City city1 = searchRegion(firstCity);
                    City city2 = searchRegion(secondCity);
                    city1.addNeighbourData(city2, distance, ferment);
                });
    }

    private City searchRegion(int cityName) {
        return cities.stream().filter(city -> city.getName() == cityName)
                .findFirst().get();
    }

    public City getRandomCity() {
        return cities.get(random.nextInt(cities.size()));
    }

    @Override
    public String toString() {
        return "Region{" +
                "cities=" + cities +
                '}';
    }
}
