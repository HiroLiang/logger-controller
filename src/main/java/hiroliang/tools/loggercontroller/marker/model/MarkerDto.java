package hiroliang.tools.loggercontroller.marker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Marker;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkerDto {
    private String name;
    private List<MarkerDto> referenceList;
    private LocalDateTime timestamp;
    private Integer expirationDays;
}
