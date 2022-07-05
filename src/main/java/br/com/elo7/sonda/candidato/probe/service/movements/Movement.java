package br.com.elo7.sonda.candidato.probe.service.movements;

import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.probe.enumeration.Command;

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
