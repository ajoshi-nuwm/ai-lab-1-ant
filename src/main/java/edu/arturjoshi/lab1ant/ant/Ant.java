package edu.arturjoshi.lab1ant.ant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.arturjoshi.lab1ant.domain.City;
import edu.arturjoshi.lab1ant.domain.NeighbourData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class Ant {
    @Getter
    private List<City> visited = new ArrayList<>();
    private Random random = new Random();
    private static final int Q = 10000;
    private static final double FORGET_K = .1;

    public Ant(City initialCity) {
        visited.add(initialCity);
    }

    public boolean visitCity() {
        City visitCity = getVisitCity();
        if (visitCity == null) {
            spreadFerment();
            return false;
        }
        visited.add(visitCity);
        return true;
    }

    public int getDistance() {
        int distance = 0;
        for (int i = 0; i < visited.size() - 1; i++) {
            City currentCity = visited.get(i);
            City nextCity = visited.get(i + 1);
            distance += currentCity.getNeighbours().get(nextCity).getDistance();
        }
        return distance;
    }

    private City getVisitCity() {
        int start = 0;
        List<Segment> segments = new ArrayList<>();
        for (City city : getAvailableCities()) {
            int prob = calculateCityTransitionProbability(city);
            Segment currentSegment = new Segment(city, start, start + prob);
            segments.add(currentSegment);
            start = start + prob;
        }
        if (start == 0) {
            return null;
        }
        int randomNumber = random.nextInt(start);
        for (Segment segment : segments) {
            if (segment.isInSegment(randomNumber)) {
                return segment.getCity();
            }
        }
        System.out.println("Error");
        return null;
    }

    private void spreadFerment() {
        int distance = getDistance();
        float delta = Q / distance;
        for (int i = 0; i < visited.size() - 1; i++) {
            City currentCity = visited.get(i);
            City nextCity = visited.get(i + 1);
            double oldFerment = currentCity.getNeighbours().get(nextCity).getFerment();
            currentCity.getNeighbours().get(nextCity).setFerment(oldFerment * FORGET_K + delta);
            nextCity.getNeighbours().get(currentCity).setFerment(oldFerment * FORGET_K + delta);
        }
    }

    private List<City> getAvailableCities() {
        City currentCity = visited.get(visited.size() - 1);
        List<City> neighbours = new ArrayList<>(currentCity.getNeighbours().keySet());
        neighbours.removeAll(visited);
        return neighbours;
    }

    private int calculateCityTransitionProbability(City city) {
        City currentCity = visited.get(visited.size() - 1);
        Double sum = currentCity.getNeighbours().values().stream().map(neighbourData ->
                neighbourData.getFerment() / neighbourData.getDistance())
                .reduce(Double::sum).orElse(0.0);
        NeighbourData candidate = currentCity.getNeighbours().get(city);
        return (int) ((candidate.getFerment() / candidate.getDistance()) / sum * 100);
    }

    @Data
    @AllArgsConstructor
    private class Segment {
        private City city;
        private int start;
        private int end;

        public boolean isInSegment(int value) {
            return value >= start && value <= end;
        }
    }
}
