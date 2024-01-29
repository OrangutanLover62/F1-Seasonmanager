package com.f1manager.controller.web;

import com.f1manager.model.dao.TrackDao;
import com.f1manager.model.dto.SeasonDto;
import com.f1manager.service.SeasonService;
import com.f1manager.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class NavigationController {
    public static final String pathLogin = "/login";
    public static final String pathHome = "/private/home";
    public static final String pathNewSeason = "/private/new/season";
    public static final String pathEditSeason = "/private/edit/season";
    @Autowired
    private final TrackService trackService;
    private final SeasonService seasonService;

    @Autowired
    public NavigationController(TrackService trackService, SeasonService seasonService) {
        this.trackService = trackService;
        this.seasonService = seasonService;
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

    @GetMapping(pathEditSeason)
    public String editSeason(
            @RequestParam(required = true) String id,
            Model model ) {

        SeasonDto season = seasonService.findById(id);
        model.addAttribute("id", season.getId());
        model.addAttribute("name", season.getName());
        model.addAttribute("races", season.getRaces());
        model.addAttribute("drivers", season.getDrivers());

        return GlobalControllerAdvice.getTemplate(pathEditSeason);
    }
}
