package pl.mg.rac.commons.api.dto;

import lombok.Value;

import java.util.Arrays;

@Value
public class ApiError {

    String message;
    String[] stackTrace;

    public ApiError(String message, StackTraceElement[] stackTrace) {
        this.message = message;
        this.stackTrace = Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .toArray(String[]::new);
    }
}
