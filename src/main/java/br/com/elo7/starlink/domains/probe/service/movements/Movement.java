package br.com.elo7.starlink.domains.probe.service.movements;

import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.enumeration.Command;

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
    public abstract Command findCommand();
}