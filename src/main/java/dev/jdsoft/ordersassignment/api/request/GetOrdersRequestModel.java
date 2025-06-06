package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
public class GetOrdersRequestModel {

    @NotNull(message = "\"from\" parameter is required and should be passed as epoch seconds.")
    private Long from;

    @NotNull(message = "\"to\" parameter is required and should be passed as epoch seconds.")
    private Long to;

    public LocalDateTime getFromDateTime() {
        return from != null ? Instant.ofEpochSecond(from).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }

    public LocalDateTime getToDateTime() {
        return to != null ? Instant.ofEpochSecond(to).atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }
}
