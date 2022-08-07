package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.direction.Direction;

import static br.com.elo7.starlink.domains.direction.Direction.*;

public enum Commands implements CommandInterface {
	LEFT('L') {
		@Override
		public Direction moveTo(Direction direction) {
			return switch (direction) {
				case N -> W;
				case W -> S;
				case S -> E;
				case E -> N;
			};
		}
	}, MOVE('M') {
		@Override
		public Direction moveTo(Direction direction) {
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
	}, RIGHT('R') {
		@Override
		public Direction moveTo(Direction direction) {
			return switch (direction) {
				case N -> Direction.E;
				case E -> Direction.S;
				case S -> Direction.W;
				case W -> Direction.N;
			};
		}
	};

	public final char command;

	Commands(char command) {
		this.command = command;
	}
}
