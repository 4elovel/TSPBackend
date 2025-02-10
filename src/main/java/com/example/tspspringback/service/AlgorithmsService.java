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

    private double distance(Coordinate a, Coordinate b) {
        double latDiff = a.getLat() - b.getLat();
        double lngDiff = a.getLng() - b.getLng();
        return Math.sqrt(latDiff * latDiff + lngDiff * lngDiff); // Euclidean distance
    }
}
