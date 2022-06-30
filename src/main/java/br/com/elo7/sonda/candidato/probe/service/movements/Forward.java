package br.com.elo7.sonda.candidato.probe.service.movements;

import br.com.elo7.sonda.candidato.probe.dto.AreaDTO;
import br.com.elo7.sonda.candidato.probe.enumeration.Command;
import org.springframework.stereotype.Component;

@Component
public class Forward extends Movement {
    public Command findCommand() {
        return Command.R;
    }
    public Movement moveTo() {
        int newX = getProbe().getArea().getX();
        int newY = getProbe().getArea().getY();
        switch (getProbe().getDirection()) {
            case N -> newY++;
            case W -> newX--;
            case S -> newY--;
            case E -> newX++;
        }
        getProbe().setArea(new AreaDTO(newX, newY));
        return this;
    }
}
