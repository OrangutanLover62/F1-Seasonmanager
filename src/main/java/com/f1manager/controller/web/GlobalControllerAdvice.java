package com.f1manager.controller.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.f1manager.model.dto.BreadcrumbDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void defaultAttributes(
        Model model,
        HttpServletRequest request,
        @CookieValue(value = "breadcrumbs", defaultValue = "") String breadcrumbs
    ) {
        String path = request.getRequestURI();
        if (path.startsWith("/private/")) {
            setBreadcrumbs(model, breadcrumbs, new BreadcrumbDto(path));
            setScripts(model, List.of(getTemplate(path)));
            setStyles(model, List.of(getTemplate(path)));
        }
    }

    public static String getTemplate(String path) {
        return path
            .substring(1)
            .replace("private/", "")
            .replaceAll("/", "-");
    }

    public static void setScripts(Model model, List<String> scripts) {
        model.addAttribute("scripts", scripts
            .stream()
            .map(script -> String.format("/js/%s.js", script))
            .toList()
        );
    }

    public static void setStyles(Model model, List<String> styles) {
        model.addAttribute("styles", styles
            .stream()
            .map(style -> String.format("/css/%s.css", style))
            .toList()
        );
    }

    private void setBreadcrumbs(Model model, String cookieBreadcrumbs, BreadcrumbDto breadcrumb) {
        try {
            List<BreadcrumbDto> breadcrumbs = new ObjectMapper().readValue(cookieBreadcrumbs, new TypeReference<>() {
            });
            breadcrumbs = filterBreadcrumbs(breadcrumbs, breadcrumb);
            breadcrumbs.add(breadcrumb);
            model.addAttribute("breadcrumbs", breadcrumbs);
        } catch (Exception e) {
            model.addAttribute(
                "breadcrumbs", List.of(new BreadcrumbDto(NavigationController.pathHome))
            );
        }
    }

    private List<BreadcrumbDto> filterBreadcrumbs(List<BreadcrumbDto> breadcrumbs, BreadcrumbDto breadcrumb) {
        for (BreadcrumbDto dto : breadcrumbs) {
            if (dto.getPath().equals(breadcrumb.getPath())) {
                return breadcrumbs.subList(0, breadcrumbs.indexOf(dto));
            }
        }
        return breadcrumbs;
    }
}
