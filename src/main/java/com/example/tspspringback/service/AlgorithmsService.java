package com.example.tspspringback.service;

import com.example.tspspringback.entity.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmsService {

    public List<Coordinate> getNearestNeighbor(List<Coordinate> coordinates) {
        if (coordinates.size() <= 1) {
            return coordinates;
        }

        Coordinate start = coordinates.getFirst();
        List<Coordinate> remainingPoints = new ArrayList<>(
                coordinates.subList(1, coordinates.size()));
        List<Coordinate> path = new ArrayList<>();
        path.add(start);

        Coordinate currentPoint = start;

        while (!remainingPoints.isEmpty()) {
            Coordinate closestPoint = findClosestPoint(currentPoint, remainingPoints);
            path.add(closestPoint);
            remainingPoints.remove(closestPoint);
            currentPoint = closestPoint;
        }

        return path;
    }

    public List<Coordinate> getBruteForce(List<Coordinate> coordinates) {
        if (coordinates.size() <= 1) {
            return coordinates;
        }
        List<List<Coordinate>> permutations = generatePermutations(
                coordinates.subList(1, coordinates.size()));
        List<Coordinate> optimalPath = null;
        double minDistance = Double.MAX_VALUE;

        for (List<Coordinate> permutation : permutations) {
            List<Coordinate> path = new ArrayList<>();
            path.add(coordinates.get(0));
            path.addAll(permutation);
            double totalDistance = calculateTotalDistance(path);

            if (totalDistance < minDistance) {
                minDistance = totalDistance;
                optimalPath = path;
            }
        }

        return optimalPath;
    }

    private List<List<Coordinate>> generatePermutations(List<Coordinate> coordinates) {
        List<List<Coordinate>> result = new ArrayList<>();
        if (coordinates.size() == 1) {
            result.add(new ArrayList<>(coordinates));
            return result;
        }

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate current = coordinates.get(i);
            List<Coordinate> remaining = new ArrayList<>(coordinates);
            remaining.remove(i);

            List<List<Coordinate>> remainingPermutations = generatePermutations(remaining);
            for (List<Coordinate> perm : remainingPermutations) {
                perm.add(0, current);
                result.add(perm);
            }
        }
        return result;
    }

    private Coordinate findClosestPoint(Coordinate currentPoint, List<Coordinate> remainingPoints) {
        Coordinate closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Coordinate point : remainingPoints) {
            double distance = distance(currentPoint, point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }

        return closestPoint;
    }

    private double calculateTotalDistance(List<Coordinate> path) {
        double totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += distance(path.get(i), path.get(i + 1));
        }
        return totalDistance;
    }

    //Man kann Euklidischer Abstand nutzen aber wir arbeiten mit latitude und longitude
    //also diese Algoritmus ist genauer.
    //Haversine formula
    //https://en.wikipedia.org/wiki/Haversine_formula
    private double distance(Coordinate a, Coordinate b) {
        final double R = 6371;
        double lat1 = Math.toRadians(a.getLat());
        double lon1 = Math.toRadians(a.getLng());
        double lat2 = Math.toRadians(b.getLat());
        double lon2 = Math.toRadians(b.getLng());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double sinDlat = Math.sin(dlat / 2);
        double sinDlon = Math.sin(dlon / 2);
        double aVal = sinDlat * sinDlat + Math.cos(lat1) * Math.cos(lat2) * sinDlon * sinDlon;
        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));

        double distance = R * c;

        return distance;
    }
}
