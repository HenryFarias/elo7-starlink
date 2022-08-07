package br.com.elo7.starlink.domains.probe.dto;

import br.com.elo7.starlink.domains.probe.enumeration.Direction;

import javax.validation.constraints.NotNull;

public class CommandDTO {

	@NotNull(message = "planetId is required")
	private Long planetId;

	@NotNull(message = "direction is required")
	private Direction direction;

	@NotNull(message = "commands are required")
	private String commands;

	public Long getPlanetId() {
		return planetId;
	}

	public void setPlanetId(Long planetId) {
		this.planetId = planetId;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
	}
}
