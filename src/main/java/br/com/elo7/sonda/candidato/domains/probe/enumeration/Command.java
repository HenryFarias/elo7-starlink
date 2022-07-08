package br.com.elo7.sonda.candidato.domains.probe.enumeration;

public enum Command {
	L('L'), M('M'), R('R');
	public final char command;
	Command(char command) {
		this.command = command;
	}


}
