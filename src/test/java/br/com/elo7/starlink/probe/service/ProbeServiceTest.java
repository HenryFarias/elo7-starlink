package br.com.elo7.starlink.probe.service;

import br.com.elo7.starlink.domains.planet.service.ObjectService;
import br.com.elo7.starlink.domains.probe.dto.AreaDTO;
import br.com.elo7.starlink.domains.probe.dto.CommandDTO;
import br.com.elo7.starlink.domains.probe.dto.ProbeDTO;
import br.com.elo7.starlink.domains.probe.entity.Probe;
import br.com.elo7.starlink.domains.probe.enumeration.Direction;
import br.com.elo7.starlink.domains.probe.repository.ProbeRepository;
import br.com.elo7.starlink.domains.probe.service.ProbeService;
import br.com.elo7.starlink.domains.probe.service.ProbeServiceImpl;
import br.com.elo7.starlink.domains.probe.service.movements.Forward;
import br.com.elo7.starlink.domains.probe.service.movements.Left;
import br.com.elo7.starlink.domains.probe.service.movements.Movement;
import br.com.elo7.starlink.domains.probe.service.movements.Right;
import br.com.elo7.starlink.exception.ApplicationException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProbeServiceTest {

	private ProbeService service;

	@Mock
	private ProbeRepository repository;

	@Mock
	private ObjectService objectService;

	@Spy
	private ModelMapper modelMapper;

	private final EasyRandom generator = new EasyRandom();

	@BeforeEach
	public void setup() {
		List<Movement> movements = Arrays.asList(new Right(), new Left(), new Forward());
		service = new ProbeServiceImpl(repository, modelMapper, movements, objectService);
	}
	
	@Test
	public void should_save_probe_success() {
		service.save(generator.nextObject(ProbeDTO.class));
		verify(repository, times(1)).save(any());
	}

	@Test
	public void should_save_probe_validation_error() {
		// todo: implements validations
	}

	@Test
	public void should_find_probe_success() {
		Probe expectedProbe = generator.nextObject(Probe.class);
		Long probeId = generator.nextLong();
		expectedProbe.setId(probeId);
		when(repository.findById(probeId))
				.thenReturn(Optional.of(expectedProbe));

		ProbeDTO probeDTO = service.find(probeId);

		assertThat(probeDTO)
				.usingRecursiveComparison()
				.isEqualTo(expectedProbe);
	}

	@Test
	public void should_find_probe_dont_exists_error() {
		ApplicationException appException = assertThrows(ApplicationException.class, () ->
			service.find(generator.nextLong())
		);
		assertEquals("Probe don't exists", appException.getMessage());
	}

	@Test
	public void should_find_all_probes_success() {
		List<Probe> probesList = Arrays.asList(
				generator.nextObject(Probe.class),
				generator.nextObject(Probe.class)
		);
		when(repository.findAll())
				.thenReturn(probesList);

		List<ProbeDTO> probes = service.findAll();

		verify(repository, times(1)).findAll();
		assertThat(probes, contains(
			hasProperty("id", is(probesList.get(0).getId())),
			hasProperty("id", is(probesList.get(1).getId()))
		));
	}

	@Test
	public void should_send_command_success() {
		Probe expectedProbe = generator.nextObject(Probe.class);
		Long expectedProbeId = generator.nextLong();
		Long expectedPlanetId = generator.nextLong();

		when(repository.findById(expectedProbeId))
				.thenReturn(Optional.of(expectedProbe));

		CommandDTO command = generator.nextObject(CommandDTO.class);
		command.setCommands("LMLMLMLMM");

		service.sendCommand(expectedProbeId, command);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any());
		verify(objectService).receiveObject(argThat(object -> {
			assertThat(object).isNotNull();
			assertEquals(expectedPlanetId, object.getPlanetId());
			assertEquals(expectedProbeId, object.getId());
			assertEquals(expectedProbe.getName(), object.getName());
			assertEquals(expectedProbe.getDescription(), object.getDescription());
			return true;
		}));
	}

	@Test
	public void should_move_probe_success() {
		String commands = "LMLMLMLMM";
		ProbeDTO probe = generator.nextObject(ProbeDTO.class);
		probe.setY(2);
		probe.setX(1);
		probe.setDirection(Direction.N);

		service.moveProbe(probe, commands);

		assertEquals(Direction.N, probe.getDirection());
		assertEquals(1, probe.getX());
		assertEquals(3, probe.getY());
	}

	@Test
	public void should_move_probe_2_success() {
		String commands = "MMRMMRMRRML";
		ProbeDTO probe = generator.nextObject(ProbeDTO.class);
		probe.setY(3);
		probe.setX(3);
		probe.setDirection(Direction.E);

		service.moveProbe(probe, commands);

		assertEquals(Direction.N, probe.getDirection());
		assertEquals(5, probe.getX());
		assertEquals(1, probe.getY());
	}

	@Test
	public void should_send_to_planet_success() {
		var expectedProbe = generator.nextObject(Probe.class);
		var area = generator.nextObject(AreaDTO.class);
		var expectedProbeId = generator.nextLong();
		var expectedPlanetId = generator.nextLong();

		area.setX(2);
		area.setY(2);
		area.setPlanetId(expectedPlanetId);

		when(repository.findById(expectedProbeId))
				.thenReturn(Optional.of(expectedProbe));

		service.sendToPlanet(expectedProbeId, area);

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any());
		verify(repository).save(argThat(probe -> {
			assertThat(probe).isNotNull();
			assertEquals(area.getX(), probe.getX());
			assertEquals(area.getY(), probe.getY());
			return true;
		}));
		verify(objectService).receiveObject(argThat(object -> {
			assertThat(object).isNotNull();
			assertEquals(expectedPlanetId, object.getPlanetId());
			assertEquals(expectedProbeId, object.getId());
			assertEquals(expectedProbe.getName(), object.getName());
			assertEquals(expectedProbe.getDescription(), object.getDescription());
			assertEquals(area.getX(), object.getX());
			assertEquals(area.getY(), object.getY());
			return true;
		}));
	}

	@Test
	public void should_send_to_planet_with_probe_already_exists_on_planet_error() {
		var probeId = generator.nextLong();
		var area = generator.nextObject(AreaDTO.class);

		when(objectService.existsByObjectIdAndPlanetId(String.valueOf(probeId), area.getPlanetId()))
				.thenReturn(true);

		ApplicationException appException = assertThrows(ApplicationException.class, () ->
				service.sendToPlanet(probeId, area)
		);
		assertEquals("Probe is already on this planet", appException.getMessage());
	}
}
