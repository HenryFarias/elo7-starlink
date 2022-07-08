package br.com.elo7.starlink.probe.service.movements;

import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.enumeration.Command;
import br.com.elo7.starlink.domains.probe.service.movements.Left;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static br.com.elo7.starlink.domains.probe.enumeration.Command.L;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeftTest {

    private final EasyRandom generator = new EasyRandom();

    @Test
    public void should_move_probe_to_west_with_left_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(W);
        probe = new Left()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(S, probe.getDirection());
    }

    @Test
    public void should_move_probe_to_north_with_left_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(N);
        probe = new Left()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(W, probe.getDirection());
    }

    @Test
    public void should_move_probe_to_east_with_left_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(E);
        probe = new Left()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(N, probe.getDirection());
    }

    @Test
    public void should_move_probe_to_south_with_left_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(S);
        probe = new Left()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(E, probe.getDirection());
    }

    @Test
    public void should_find_command_left_success() {
        Command command = new Left().findCommand();
        assertEquals(L, command);
    }
}
