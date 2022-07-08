package br.com.elo7.starlink.domains.probe.service.movements;

import br.com.elo7.starlink.domains.probe.enumeration.Direction;
import br.com.elo7.starlink.domains.probe.enumeration.Command;
import br.com.elo7.starlink.domains.probe.enumeration.Direction;
import org.springframework.stereotype.Component;

@Component
public class Right extends Movement {
    public Command findCommand() {
        return Command.R;
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
