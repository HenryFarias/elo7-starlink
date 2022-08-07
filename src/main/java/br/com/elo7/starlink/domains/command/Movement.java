package br.com.elo7.starlink.domains.command;

import br.com.elo7.starlink.domains.probe.ProbeDTO;

public abstract class Movement {

    protected ProbeDTO probe;

    public ProbeDTO getProbe() {
        return probe;
    }

    public Movement setProbe(ProbeDTO probe) {
        this.probe = probe;
        return this;
    }

    public abstract Movement moveTo();
    public abstract Commands findCommand();
}
