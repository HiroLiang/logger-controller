package hiroliang.tools.loggercontroller.marker.model;

import hiroliang.tools.loggercontroller.marker.StandardMarker;
import hiroliang.tools.loggercontroller.util.MarkerFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkerListDto {

    List<MarkerDto> markers;

    // 轉換 DTO 為可用物件
    public List<StandardMarker> toStandardMarkerList() {
        return convert(this.markers);
    }

    // 轉換為 List<StandardMarker> 物件
    private List<StandardMarker> convert(List<MarkerDto> markersDto) {
        final List<StandardMarker> markers = new ArrayList<>();
        // 遍歷 DTO List
        if (markersDto != null){
           for (MarkerDto markerDto : markersDto) {
                StandardMarker marker = MarkerFactory
                     .create(markerDto.getName(), markerDto.getTimestamp(), markerDto.getExpirationDays());
                // 若有 reference 再 call convert() 做一個 List
                if(!markerDto.getReferenceList().isEmpty()){
                    List<StandardMarker> childMarkers = convert(markerDto.getReferenceList());
                    // 加入倒 reference 裡面
                    for(StandardMarker childMarker : childMarkers) {
                        assert marker != null;
                        marker.addChild(childMarker);
                    }
                }
                markers.add(marker);
           }
        }
        return markers;
    }

}
