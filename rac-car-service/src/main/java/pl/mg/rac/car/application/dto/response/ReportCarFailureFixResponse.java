package pl.mg.rac.car.application.dto.response;

public record ReportCarFailureFixResponse(String vin,
                                          boolean failure) {
}
