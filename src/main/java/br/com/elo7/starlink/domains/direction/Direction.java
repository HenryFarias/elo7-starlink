package br.com.elo7.starlink.domains.direction;

public enum Direction {
	N('N'), W('W'), E('E'), S('S');
	public final char direction;
	Direction(char direction) {
		this.direction = direction;
	}
}
