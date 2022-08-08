package br.com.elo7.starlink.domains.area;

import br.com.elo7.starlink.domains.direction.Direction;
import br.com.elo7.starlink.domains.planet.Planet;
import br.com.elo7.starlink.infra.exception.ApplicationException;

public class Area {

    private Integer x;
    private Integer y;

    public Area() {}

    public Area(String area) {
        if (area != null) {
            var split = area.split(",");
            x = Integer.valueOf(split[0]);
            y = Integer.valueOf(split[1]);
        }
    }

    public void validCoordinatesAreOutOfPlanet(Planet planet) {
        var out = x > planet.getWidth() ||
                x < 0 ||
                y > planet.getHeight() ||
                y < 0;
        if (out) {
            throw new ApplicationException("Coordinates are out of planet");
        }
    }

    public void calculateNewArea(Direction direction) {
        switch (direction) {
            case NORTH -> y++;
            case WEST -> x--;
            case SOUTH -> y--;
            case EAST -> x++;
        }
    }

    @Override
    public String toString() {
        if (x == null || y == null) {
            return null;
        }
        return x + "," + y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
