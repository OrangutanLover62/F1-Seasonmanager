package com.f1manager.controller.web;

import com.f1manager.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
