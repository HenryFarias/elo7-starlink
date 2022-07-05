package br.com.elo7.sonda.candidato.probe.service.movements;

import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.probe.enumeration.Command;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static br.com.elo7.sonda.candidato.probe.enumeration.Command.R;
import static br.com.elo7.sonda.candidato.probe.enumeration.Direction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RightTest {

    private final EasyRandom generator = new EasyRandom();

    @Test
    public void should_move_probe_to_west_with_right_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(W);
        probe = new Right()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(N, probe.getDirection());
    }

    @Test
    public void should_move_probe_to_north_with_right_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(N);
        probe = new Right()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(E, probe.getDirection());
    }

    @Test
    public void should_move_probe_to_east_with_right_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(E);
        probe = new Right()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(S, probe.getDirection());
    }

    @Test
    public void should_move_probe_to_south_with_right_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(S);
        probe = new Right()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(W, probe.getDirection());
    }

    @Test
    public void should_find_command_right_success() {
        Command command = new Right().findCommand();
        assertEquals(R, command);
    }
}
