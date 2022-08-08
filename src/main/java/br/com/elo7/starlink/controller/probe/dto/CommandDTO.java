package br.com.elo7.starlink.controller.probe.dto;

import javax.validation.constraints.NotNull;

public class CommandDTO {

	@NotNull(message = "commands are required")
	private String commands;

	public String getCommands() {
		return commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
	}
}
