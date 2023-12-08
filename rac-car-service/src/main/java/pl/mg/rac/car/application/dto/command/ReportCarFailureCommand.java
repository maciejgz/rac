package pl.mg.rac.car.application.dto.command;

public record ReportCarFailureCommand(
        String vin,
        String failureReason
) {
}
