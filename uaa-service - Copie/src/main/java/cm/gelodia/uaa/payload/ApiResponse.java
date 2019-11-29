package cm.gelodia.uaa.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse {
    private Boolean success;
    private String message;
}
