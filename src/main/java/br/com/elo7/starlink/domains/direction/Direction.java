package br.com.elo7.starlink.domains.direction;

import java.util.Arrays;

public enum Direction {
	NORTH('N'), WEST('W'), EAST('E'), SOUTH('S');
	public final char name;
	Direction(char name) {
		this.name = name;
	}

	public static Direction find(char d) {
		return Arrays.stream(Direction.values())
				.filter(direction -> direction.name == d)
				.findFirst()
				.get();
	}
}
