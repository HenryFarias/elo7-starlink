package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;

public interface CommandInterface {
    Direction turn(Direction direction);
}