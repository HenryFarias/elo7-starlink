package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;

public class Forward extends Movement {
    public Commands findCommand() {
        return Commands.MOVE;
    }
    public Movement moveTo(Direction direction) {
        int newX = getProbe().getX();
        int newY = getProbe().getY();
        switch (direction) {
            case N -> newY++;
            case W -> newX--;
            case S -> newY--;
            case E -> newX++;
        }
        getProbe().setX(newX);
        getProbe().setY(newY);
        return this;
    }
}
