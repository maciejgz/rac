package pl.mg.rac.rent.domain.model;

import pl.mg.rac.commons.value.Location;

public class Rent {

    private String id;
    private String username;
    private String vin;
    private Location startLocation;
    private double distanceTraveled;
    private Location endLocation;
    private RentStatus status;

}
