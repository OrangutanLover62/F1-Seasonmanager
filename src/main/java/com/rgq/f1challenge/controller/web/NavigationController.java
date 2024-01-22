package com.rgq.f1challenge.controller.web;

import com.rgq.f1challenge.controller.rest.TrackController;
import com.rgq.f1challenge.model.dto.TrackDto;
import com.rgq.f1challenge.service.DriverService;
import com.rgq.f1challenge.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NavigationController {
    public static final String pathLogin = "/login";
    public static final String pathHome = "/private/home";
    public static final String pathNewSeason = "/private/new/season";
    @Autowired
    private final TrackService trackService;

    @Autowired
    public NavigationController(TrackService trackService) {
        this.trackService = trackService;
    }


    @GetMapping("/")
    public String index() {
        return login();
    }

    @GetMapping(pathLogin)
    public String login() {
        return GlobalControllerAdvice.getTemplate(pathLogin);
    }

    @GetMapping(pathHome)
    public String home() { return GlobalControllerAdvice.getTemplate(pathHome); }

    @GetMapping(pathNewSeason)
    public String newSeason() {
        return GlobalControllerAdvice.getTemplate(pathNewSeason);
    }
}
