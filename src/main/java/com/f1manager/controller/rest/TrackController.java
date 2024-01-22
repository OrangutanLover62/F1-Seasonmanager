package com.f1manager.controller.rest;

import com.f1manager.service.ErrorService;
import com.f1manager.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/track")
@AllArgsConstructor
public class TrackController {
    private final ErrorService errorService;
    private final TrackService trackService;

    @GetMapping("/update")
    public ResponseEntity<Object> update() {
        try {
            trackService.updateByHtml();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }

    @GetMapping("/allTracks")
    public ResponseEntity<Object> getAllTracks() {
        try {
            return ResponseEntity.ok().body(trackService.findAll());
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }


    @GetMapping("/selectedTrack")
    public ResponseEntity<Object> getSelectedTrack() {
        try {
            return ResponseEntity.ok().body(trackService.findAll());
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }

    @GetMapping("/byId")
    public ResponseEntity<Object> getTrackByName(
            @RequestParam(required = true) String id) {
        try {
            return ResponseEntity.ok().body(trackService.findById(id));
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }


}
