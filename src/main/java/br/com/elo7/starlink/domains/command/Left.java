package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;

public class Left extends Movement {

    public Commands findCommand() {
        return Commands.LEFT;
    }

    public Movement moveTo() {
        Direction newDirection = switch (probe.getDirection()) {
            case N -> Direction.W;
            case W -> Direction.S;
            case S -> Direction.E;
            case E -> Direction.N;
        };
        probe.setDirection(newDirection);
        return this;
    }
}
