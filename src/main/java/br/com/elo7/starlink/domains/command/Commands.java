package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.area.Area;
import br.com.elo7.starlink.domains.direction.Direction;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static br.com.elo7.starlink.domains.direction.Direction.*;

public enum Commands implements CommandInterface {
	LEFT('L') {
		@Override
		public Direction turn(Direction direction) {
			return switch (direction) {
				case NORTH -> WEST;
				case WEST -> SOUTH;
				case SOUTH -> EAST;
				case EAST -> NORTH;
			};
		}
	}, MOVE('M') {
		@Override
		public Direction turn(Direction direction) {
			return direction;
		}
	}, RIGHT('R') {
		@Override
		public Direction turn(Direction direction) {
			return switch (direction) {
				case NORTH -> Direction.EAST;
				case EAST -> Direction.SOUTH;
				case SOUTH -> Direction.WEST;
				case WEST -> Direction.NORTH;
			};
		}
	};

	public final char name;

	Commands(char name) {
		this.name = name;
	}

	public static boolean isMove(char command) {
		return command == MOVE.name;
	}

	public static Commands find(char cmd) {
		return Arrays.stream(Commands.values())
				.filter(command -> command.name == cmd)
				.findFirst()
				.get();
	}

	public static void run(String commands, Direction direction, Area area, Consumer<Area> onMove, BiConsumer<Direction, Area> onFinish) {
		for (char cmd : commands.toCharArray()) {
			direction = Commands.find(cmd).turn(direction);
			if (Commands.isMove(cmd)) {
				area.calculateNewArea(direction);
				onMove.accept(area);
			}
			onFinish.accept(direction, area);
		}
	}
}
