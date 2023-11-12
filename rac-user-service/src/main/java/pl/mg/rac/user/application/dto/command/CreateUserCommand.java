package pl.mg.rac.user.application.dto.command;


import pl.mg.rac.commons.value.Location;

public record CreateUserCommand(String name, Location location) {


}
