package br.com.elo7.starlink.probe.service.movements;

import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.enumeration.Command;
import br.com.elo7.starlink.domains.probe.service.movements.Forward;
import br.com.elo7.starlink.domains.probe.service.movements.Forward;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static br.com.elo7.starlink.domains.probe.enumeration.Command.M;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForwardTest {

    private final EasyRandom generator = new EasyRandom();

    @Test
    public void should_move_probe_to_west_with_forward_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(W);
        probe.setX(2);
        probe.setY(2);
        probe = new Forward()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(1, probe.getX());
        assertEquals(2, probe.getY());
    }

    @Test
    public void should_move_probe_to_north_with_forward_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(N);
        probe.setX(2);
        probe.setY(2);
        probe = new Forward()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(2, probe.getX());
        assertEquals(3, probe.getY());
    }

    @Test
    public void should_move_probe_to_east_with_forward_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(E);
        probe.setX(2);
        probe.setY(2);
        probe = new Forward()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(3, probe.getX());
        assertEquals(2, probe.getY());
    }

    @Test
    public void should_move_probe_to_south_with_forward_command_success() {
        var probe = generator.nextObject(ProbeDTO.class);
        probe.setDirection(S);
        probe.setX(2);
        probe.setY(2);
        probe = new Forward()
                .setProbe(probe)
                .moveTo()
                .getProbe();
        assertEquals(2, probe.getX());
        assertEquals(1, probe.getY());
    }

    @Test
    public void should_find_command_forward_success() {
        Command command = new Forward().findCommand();
        assertEquals(M, command);
    }
}
