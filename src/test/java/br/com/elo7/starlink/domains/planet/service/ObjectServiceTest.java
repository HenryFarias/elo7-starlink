package br.com.elo7.starlink.domains.planet.service;

import br.com.elo7.starlink.domains.object.ObjectDTO;
import br.com.elo7.starlink.domains.object.ObjectInterface;
import br.com.elo7.starlink.domains.object.Object;
import br.com.elo7.starlink.domains.planet.PlanetDTO;
import br.com.elo7.starlink.domains.planet.PlanetInterface;
import br.com.elo7.starlink.infra.entity.Planet;
import br.com.elo7.starlink.domains.object.ObjectRepository;
import br.com.elo7.starlink.infra.exception.ApplicationException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ObjectServiceTest {

	@InjectMocks
	private ObjectInterface service = new Object();

	@Mock
	private ObjectRepository repository;

	@Mock
	private PlanetInterface planetService;

	@Spy
	private ModelMapper modelMapper;

	private final EasyRandom generator = new EasyRandom();

	@Test
	public void should_update_coordinates_success() {
		var x = 1;
		var y = 2;
		var objectToUpdate = generator.nextObject(br.com.elo7.starlink.infra.entity.Object.class);
		objectToUpdate.setX(2);
		objectToUpdate.setY(1);

		service.updateCoordinates(objectToUpdate, x, y);

		verify(repository).save(argThat(object -> {
			Assertions.assertEquals(x, object.getX());
			Assertions.assertEquals(y, object.getY());
			return true;
		}));
	}

	@Test
	public void should_receive_object_that_not_exists_success() {
		var objectDTO = generator.nextObject(ObjectDTO.class);
		var planet = generator.nextObject(PlanetDTO.class);

		objectDTO.setX(0);
		objectDTO.setY(0);
		objectDTO.setPlanetId(planet.getId());
		planet.setHeight(5);
		planet.setWidth(5);

		when(planetService.find(planet.getId()))
				.thenReturn(planet);
		when(repository.findByObjectIdAndPlanet_Id(objectDTO.getObjectId(), planet.getId()))
				.thenReturn(Optional.empty());

		service.receiveObject(objectDTO);

		verify(repository).save(argThat(object -> {
			assertEquals(planet.getId(), object.getPlanet().getId());
			return true;
		}));
	}

	@Test
	public void should_receive_object_that_exists_success() {
		var objectDTO = generator.nextObject(ObjectDTO.class);
		var planet = generator.nextObject(PlanetDTO.class);
		var currentObject = generator.nextObject(br.com.elo7.starlink.infra.entity.Object.class);

		objectDTO.setX(0);
		objectDTO.setY(0);
		objectDTO.setPlanetId(planet.getId());
		planet.setHeight(5);
		planet.setWidth(5);
		currentObject.setPlanet(modelMapper.map(planet, Planet.class));

		when(planetService.find(planet.getId()))
				.thenReturn(planet);
		when(repository.findByObjectIdAndPlanet_Id(objectDTO.getObjectId(), planet.getId()))
				.thenReturn(Optional.of(currentObject));

		service.receiveObject(objectDTO);

		verify(repository).save(argThat(object -> {
			assertEquals(planet.getId(), object.getPlanet().getId());
			return true;
		}));
	}

	@Test
	public void should_receive_object_with_coordinates_out_of_planet_error() {
		var objectDTO = generator.nextObject(ObjectDTO.class);
		var planet = generator.nextObject(PlanetDTO.class);

		objectDTO.setX(7);
		objectDTO.setPlanetId(planet.getId());
		planet.setWidth(5);

		when(planetService.find(planet.getId()))
				.thenReturn(planet);

		ApplicationException appException = assertThrows(ApplicationException.class, () ->
			service.receiveObject(objectDTO)
		);
		assertEquals("Object is off planet", appException.getMessage());
	}

	@Test
	public void should_receive_object_with_another_object_in_current_coordinates_error() {
		var objectDTO = generator.nextObject(ObjectDTO.class);
		var planet = generator.nextObject(PlanetDTO.class);

		objectDTO.setX(0);
		objectDTO.setY(0);
		objectDTO.setPlanetId(planet.getId());
		planet.setHeight(5);
		planet.setWidth(5);
		planet.getObjects().get(0).setX(objectDTO.getX());
		planet.getObjects().get(0).setY(objectDTO.getY());

		when(planetService.find(planet.getId()))
				.thenReturn(planet);

		ApplicationException appException = assertThrows(ApplicationException.class, () ->
				service.receiveObject(objectDTO)
		);
		assertEquals("There is already an object in this area", appException.getMessage());
	}

	@Test
	public void should_validate_exists_probe_on_planet_success() {
		var objectDTO = generator.nextObject(ObjectDTO.class);
		var planet = generator.nextObject(PlanetDTO.class);

		when(repository.existsByObjectIdAndPlanet_Id(objectDTO.getId().toString(), planet.getId()))
				.thenReturn(true);

		boolean exists = service.existsByObjectIdAndPlanetId(objectDTO.getId().toString(), planet.getId());

		assertTrue(exists);
	}
}
