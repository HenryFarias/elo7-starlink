package br.com.elo7.sonda.candidato.domains.probe.service.movements;

import br.com.elo7.sonda.candidato.domains.probe.enumeration.Command;
import org.springframework.stereotype.Component;

@Component
public class Forward extends Movement {
    public Command findCommand() {
        return Command.M;
    }
    public Movement moveTo() {
        int newX = getProbe().getX();
        int newY = getProbe().getY();
        switch (probe.getDirection()) {
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
