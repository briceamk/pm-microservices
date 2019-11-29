package cm.gelodia.uaa.exception;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExceptionInfoDetails {
    private Integer status;
    private LocalDateTime timestamp;
    private String message;
    private String detail;
}
