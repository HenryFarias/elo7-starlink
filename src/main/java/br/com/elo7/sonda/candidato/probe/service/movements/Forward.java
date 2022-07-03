package br.com.elo7.sonda.candidato.probe.service.movements;

import br.com.elo7.sonda.candidato.probe.enumeration.Command;
import br.com.elo7.sonda.candidato.probe.enumeration.Direction;
import org.springframework.stereotype.Component;

@Component
public class Forward extends Movement {
    public Command findCommand() {
        return Command.M;
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