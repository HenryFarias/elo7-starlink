package br.com.elo7.starlink.domains.planet.service;

import br.com.elo7.starlink.domains.planet.PlanetInterface;
import br.com.elo7.starlink.domains.planet.Planet;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import br.com.elo7.starlink.domains.planet.PlanetDTO;
import br.com.elo7.starlink.domains.planet.PlanetRepository;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

	@InjectMocks
	private PlanetInterface service = new Planet();

	@Mock
	private PlanetRepository repository;

	@Spy
	private ModelMapper modelMapper;

	private final EasyRandom generator = new EasyRandom();
	
	@Test
	public void should_save_planet_success() {
		service.save(generator.nextObject(PlanetDTO.class));
		verify(repository, times(1)).save(any());
	}

	@Test
	public void should_find_planet_success() {
		br.com.elo7.starlink.infra.entity.Planet expectedPlanet = generator.nextObject(br.com.elo7.starlink.infra.entity.Planet.class);
		Long planetId = generator.nextLong();
		expectedPlanet.setId(planetId);

		when(repository.findById(planetId))
				.thenReturn(Optional.of(expectedPlanet));

		PlanetDTO planet = service.find(planetId);

		assertEquals(expectedPlanet.getId(), planet.getId());
		assertEquals(expectedPlanet.getDescription(), planet.getDescription());
		assertEquals(expectedPlanet.getName(), planet.getName());
		assertEquals(expectedPlanet.getHeight(), planet.getHeight());
		assertEquals(expectedPlanet.getWidth(), planet.getWidth());
		assertEquals(planet.getObjects().size(), expectedPlanet.getObjects().size());
		assertEquals(planet.getObjects().get(0).getId(), expectedPlanet.getObjects().get(0).getId());
	}

	@Test
	public void should_find_planet_dont_exists_error() {
		ApplicationException appException = assertThrows(ApplicationException.class, () ->
			service.find(generator.nextLong())
		);
		assertEquals("Planet not found", appException.getMessage());
	}

	@Test
	public void should_find_all_planets_success() {
		List<br.com.elo7.starlink.infra.entity.Planet> planetsExpected = Arrays.asList(
				generator.nextObject(br.com.elo7.starlink.infra.entity.Planet.class),
				generator.nextObject(br.com.elo7.starlink.infra.entity.Planet.class)
		);
		when(repository.findAll())
				.thenReturn(planetsExpected);

		List<PlanetDTO> planets = service.findAll();

		verify(repository, times(1)).findAll();
		assertEquals(planets.size(), planetsExpected.size());
		assertThat(planets, contains(
			hasProperty("id", is(planetsExpected.get(0).getId())),
			hasProperty("id", is(planetsExpected.get(1).getId()))
		));
	}
}
