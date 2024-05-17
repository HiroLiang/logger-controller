package hiroliang.tools.loggercontroller.demo.controller;

import hiroliang.tools.loggercontroller.marker.model.MarkerDto;
import hiroliang.tools.loggercontroller.marker.model.MarkerListDto;
import hiroliang.tools.loggercontroller.util.MarkerHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/marker")
public class DemoController {

    @GetMapping("/test")
    public void testLog() {
        log.info("Test normal log ... ");
        log.debug("log debug");
        log.trace("log trace");
        log.warn("log warn");
        log.error("log error");
        System.out.println("""
                
                -------------------------------------------------------------------------------
                """);
        log.info("Test marker log ... ");
        log.info(MarkerHandler.getMarker("Default").toString());
        log.info(MarkerHandler.getMarker("Default"), "Default marker");
        log.info(MarkerHandler.getMarker("Standard").toString());
        log.info(MarkerHandler.getMarker("Standard"), "Standard marker");
        System.out.println("""
                
                -------------------------------------------------------------------------------
                """);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Marker> getMarker(@PathVariable String name) {
        return ResponseEntity.ok(MarkerHandler.getMarker(name));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Marker>> getAllMarkers() {
        List<Marker> markers = MarkerHandler.getModelList();
        return ResponseEntity.ok(markers);
    }

    @PostMapping("/update")
    public void updateMarkers(@RequestBody List<MarkerDto> markerList) {
        log.info("get dto : {}", markerList);
        MarkerListDto markerListDto = new MarkerListDto(markerList);
        MarkerHandler.init(markerListDto.toStandardMarkerList());
    }
}
