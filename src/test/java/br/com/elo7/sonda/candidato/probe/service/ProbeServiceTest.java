package br.com.elo7.sonda.candidato.probe.service;

import br.com.elo7.sonda.candidato.probe.dto.ProbeDTO;
import br.com.elo7.sonda.candidato.probe.entity.Probe;
import br.com.elo7.sonda.candidato.probe.repository.ProbeRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

	@InjectMocks
	private ProbeService service = new ProbeServiceImpl();

	@Mock
	private ProbeRepository repository;

	@Spy
	private ModelMapper modelMapper;

	private final EasyRandom generator = new EasyRandom();
	
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
	public void should_find_probe_success() throws Exception {
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
	public void should_find_probe_but_probe_dont_exists_error() {
		Exception appException = assertThrows(Exception.class, () ->
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
		// todo:
	}

	@Test
	public void should_move_probe_success() {
		// todo:
	}
}
