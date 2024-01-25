package com.f1manager.controller.rest;

import com.f1manager.model.exception.NoValueException;
import com.f1manager.service.DriverService;
import com.f1manager.service.ErrorService;
import com.f1manager.model.dto.DriverDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/driver")
@AllArgsConstructor
public class DriverController {
    private final ErrorService errorService;
    private final DriverService driverService;

    @GetMapping("/all")
    public ResponseEntity<Object> all() {
        try {
            return ResponseEntity
                .ok()
                .body(driverService.findAll());
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }

    @GetMapping("/find/html")
    public ResponseEntity<Object> findHtml(
        @RequestParam(required = false) String id,
        @RequestParam(required = false) String name
    ) {
        try {
            if (id != null) {
                return ResponseEntity
                    .ok()
                    .body(driverService.findHtmlById(id));
            }
            if (name != null) {
                return ResponseEntity
                    .ok()
                    .body(driverService.findHtmlByName(name));
            }
            throw new NoValueException("RequestParam id or name must be defined");
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }


    @PostMapping("/findById")
    public DriverDto findById(
            @RequestParam(required = true) String id)
    { return driverService.findById(id); }


    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody DriverDto driverDto) {
        try {
            driverService.validate(driverDto);
            return ResponseEntity
                .ok()
                .body(driverService.save(driverDto));
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }
}
