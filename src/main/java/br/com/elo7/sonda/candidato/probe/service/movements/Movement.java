package br.com.elo7.sonda.candidato.probe.service.movements;

import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.probe.enumeration.Command;
import br.com.elo7.sonda.candidato.probe.enumeration.Direction;

public abstract class Movement {

    protected ProbeDTO probe;

    public ProbeDTO getProbe() {
        return probe;
    }

    public Movement setProbe(ProbeDTO probe) {
        this.probe = probe;
        return this;
    }

    public abstract Movement moveTo(Direction direction);
    public abstract Command findCommand();
}
