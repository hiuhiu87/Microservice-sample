package dev.nmh.productservice.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseModel {

    private HttpStatus statusCode;

    private LocalDateTime timestamp;

    private String message;

    private String description;

    private Object data;

}
