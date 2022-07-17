package br.com.elo7.starlink.domains.planet.dto;

import br.com.elo7.starlink.exception.ApplicationException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlanetDtoTest {

    private final EasyRandom generator = new EasyRandom();

    @Test
    public void should_verify_coordinate_inside_planet_with_x_bigger_then_planet_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
            planet.coordinatesIsInsidePlanet(7, 0)
        );
        assertEquals("Object is off planet", appException.getMessage());
    }

    @Test
    public void should_verify_coordinate_inside_planet_with_y_bigger_then_planet_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
                planet.coordinatesIsInsidePlanet(0, 7)
        );
        assertEquals("Object is off planet", appException.getMessage());
    }

    @Test
    public void should_verify_coordinate_inside_planet_with_x_and_y_bigger_then_planet_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
                planet.coordinatesIsInsidePlanet(7, 7)
        );
        assertEquals("Object is off planet", appException.getMessage());
    }

    @Test
    public void should_verify_coordinate_inside_planet_with_x_less_then_planet_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
                planet.coordinatesIsInsidePlanet(-7, 0)
        );
        assertEquals("Object is off planet", appException.getMessage());
    }

    @Test
    public void should_verify_coordinate_inside_planet_with_y_less_then_planet_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
                planet.coordinatesIsInsidePlanet(0, -7)
        );
        assertEquals("Object is off planet", appException.getMessage());
    }

    @Test
    public void should_verify_coordinate_inside_planet_with_x_and_y_less_then_planet_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
            planet.coordinatesIsInsidePlanet(-7, -7)
        );
        assertEquals("Object is off planet", appException.getMessage());
    }

    @Test
    public void should_verify_there_is_no_object_at_coordinate_with_object_in_coordinate_error() {
        var planet = generator.nextObject(PlanetDTO.class);
        planet.setWidth(5);
        planet.setHeight(5);
        planet.getObjects().get(0).setX(2);
        planet.getObjects().get(0).setY(2);
        ApplicationException appException = assertThrows(ApplicationException.class, () ->
            planet.thereIsNoObjectAtCoordinates(2, 2)
        );
        assertEquals("There is already an object in this area", appException.getMessage());
    }
}
