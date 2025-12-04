package io.github.diegoms.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private String message;
    private Collection<FieldError> erros;

    public ResponseError(String message, Collection<FieldError> erros) {
        this.message = message;
        this.erros = erros;
    }

    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {
        List<FieldError> erros = violations.stream().map(cv ->
                new FieldError(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()
                )
        ).collect(Collectors.toList());

        return new ResponseError("Validation Failed", erros);
    }

    public Response withStatusCode(int statusCode) {
        return Response.status(statusCode).entity(this).build();
    }
}
