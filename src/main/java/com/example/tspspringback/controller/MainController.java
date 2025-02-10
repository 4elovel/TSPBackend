package com.example.tspspringback.controller;

import com.example.tspspringback.entity.Coordinate;
import com.example.tspspringback.request.CoordinatesRequest;
import com.example.tspspringback.service.AlgorithmsService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    private final List<Coordinate> mockData = List.of(
            new Coordinate(49.85215166777001, 7.254445377281657),
            new Coordinate(51.01375465718821, 13.103032629036584),
            new Coordinate(51.890053935216926, 11.56393072068002),
            new Coordinate(47.90161354142077, 10.794379766501736),
            new Coordinate(48.151428143221224, 7.716175949788649),
            new Coordinate(49.224772722794825, 13.630724711901697)
    );

    AlgorithmsService algorithmsService = new AlgorithmsService();

    @GetMapping("/hello")
    public Map<String, String> sayHello() {
        return Map.of("message", "Hello, World!");
    }

    @GetMapping("/coordinates")
    public List<Coordinate> getCoordinates() {
        return mockData;
    }

    @PostMapping("/nearestNeighbor")
    public List<Coordinate> getNearestNeighbor(@RequestBody CoordinatesRequest coordinatesRequest) {
        List<Coordinate> points = coordinatesRequest.getPoints();
        points.forEach(point -> System.out.println(
                "Received: lat=" + point.getLat() + ", lng=" + point.getLng()));
        return algorithmsService.getNearestNeighbor(points);
    }
}
