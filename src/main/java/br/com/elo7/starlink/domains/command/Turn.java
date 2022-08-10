package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;
import br.com.elo7.starlink.infra.exception.ApplicationException;

import java.util.Arrays;

import static br.com.elo7.starlink.domains.direction.Direction.*;

public enum Turn implements TurnInterface {
	LEFT('L') {
		@Override
		public Direction execute(Direction direction) {
			return switch (direction) {
				case NORTH -> WEST;
				case WEST -> SOUTH;
				case SOUTH -> EAST;
				case EAST -> NORTH;
			};
		}
	}, RIGHT('R') {
		@Override
		public Direction execute(Direction direction) {
			return switch (direction) {
				case NORTH -> Direction.EAST;
				case EAST -> Direction.SOUTH;
				case SOUTH -> Direction.WEST;
				case WEST -> Direction.NORTH;
			};
		}
	};

	public final char name;

	Turn(char name) {
		this.name = name;
	}

	public static Turn find(char cmd) {
		return Arrays.stream(Turn.values())
				.filter(command -> command.name == cmd)
				.findFirst()
				.orElseThrow(() -> new ApplicationException("Command not recognized"));
	}
}
