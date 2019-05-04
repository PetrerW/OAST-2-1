package com.oast;

import java.util.Objects;

public class Point {

    private Integer demandId;
    private Integer pathId;

    public Point(Integer demandId, Integer pathId) {
        this.demandId = demandId;
        this.pathId = pathId;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(demandId, point.demandId) &&
                Objects.equals(pathId, point.pathId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(demandId, pathId);
    }
}
