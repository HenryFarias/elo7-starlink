package br.com.elo7.sonda.candidato.probe.dto;

public class CommandDTO {
	private Long planetId;
	private char direction;
	private String commands;

	public Long getPlanetId() {
		return planetId;
	}

	public void setPlanetId(Long planetId) {
		this.planetId = planetId;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
	}
}
