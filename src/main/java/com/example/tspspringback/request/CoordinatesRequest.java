package com.example.tspspringback.request;

import com.example.tspspringback.entity.Coordinate;
import java.util.List;

public class CoordinatesRequest {

    private List<Coordinate> points;

    public List<Coordinate> getPoints() {
        return points;
    }

    public void setPoints(List<Coordinate> points) {
        this.points = points;
    }
}
