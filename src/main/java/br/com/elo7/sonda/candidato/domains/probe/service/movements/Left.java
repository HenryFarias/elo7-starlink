package br.com.elo7.sonda.candidato.domains.probe.service.movements;

import br.com.elo7.sonda.candidato.domains.probe.enumeration.Direction;
import br.com.elo7.sonda.candidato.domains.probe.enumeration.Command;
import org.springframework.stereotype.Component;

@Component
public class Left extends Movement {

    public Command findCommand() {
        return Command.L;
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
