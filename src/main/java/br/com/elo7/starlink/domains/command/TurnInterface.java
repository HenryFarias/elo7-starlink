package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;

public interface TurnInterface {
    Direction execute(Direction direction);
}
