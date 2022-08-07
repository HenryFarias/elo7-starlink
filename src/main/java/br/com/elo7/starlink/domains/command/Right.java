package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;

public class Right extends Movement {
    public Commands findCommand() {
        return Commands.R;
    }
    public Movement moveTo() {
        Direction newDirection = switch (probe.getDirection()) {
            case N -> Direction.E;
            case E -> Direction.S;
            case S -> Direction.W;
            case W -> Direction.N;
        };
        probe.setDirection(newDirection);
        return this;
    }
}
