package edu.arturjoshi.lab1ant.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
@RequiredArgsConstructor
public class City {
    private @NonNull int name;
    private Map<City, NeighbourData> neighbours = new HashMap<>();

    public void addNeighbourData(City city, int distance, int ferment) {
        neighbours.put(city, new NeighbourData(distance, ferment));
        city.neighbours.put(this, new NeighbourData(distance, ferment));
    }
}
