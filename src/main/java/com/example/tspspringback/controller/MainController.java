package com.example.tspspringback.controller;

import com.example.tspspringback.entity.Coordinate;
import com.example.tspspringback.request.CoordinatesRequest;
import com.example.tspspringback.service.AlgorithmsService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    AlgorithmsService algorithmsService = new AlgorithmsService();

    static private int[][] parseToIndexes(List<Coordinate> startPositions, List<Coordinate> path) {
        List<int[]> indexPairs = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            Coordinate fromCoordinate = path.get(i);
            Coordinate toCoordinate = path.get(i + 1);
            int fromDotIndex = startPositions.indexOf(fromCoordinate);
            int toDotIndex = startPositions.indexOf(toCoordinate);
            indexPairs.add(new int[]{fromDotIndex, toDotIndex});
        }
        int lastDotIndex = startPositions.indexOf(path.get(path.size() - 1));
        int firstDotIndex = startPositions.indexOf(path.get(0));
        indexPairs.add(new int[]{lastDotIndex, firstDotIndex});
        return indexPairs.toArray(new int[0][]);
    }

    @PostMapping("/nearestNeighbor")
    public int[][] getNearestNeighbor(@RequestBody CoordinatesRequest coordinatesRequest) {
        List<Coordinate> points = coordinatesRequest.getPoints();
        points.forEach(point -> System.out.println(
                "Received: lat=" + point.getLat() + ", lng=" + point.getLng()));
        List<Coordinate> path = algorithmsService.getNearestNeighbor(points);
        System.out.println(Arrays.deepToString(parseToIndexes(points, path)));

        return parseToIndexes(points, path);
    }

    @PostMapping("/bruteForce")
    public int[][] getBruteForce(@RequestBody CoordinatesRequest coordinatesRequest) {
        List<Coordinate> points = coordinatesRequest.getPoints();
        points.forEach(point -> System.out.println(
                "Received: lat=" + point.getLat() + ", lng=" + point.getLng()));

        List<Coordinate> path = algorithmsService.getBruteForce(points);
        System.out.println(Arrays.deepToString(parseToIndexes(points, path)));

        return parseToIndexes(points, path);
    }
}
