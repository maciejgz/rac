package pl.mg.rac.car.application.dto.response;

public record ReportCarFailureResponse(String vin, boolean failure, String failureReason) {
}
