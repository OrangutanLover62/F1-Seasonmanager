package com.f1manager.model.dto;

import com.f1manager.controller.web.NavigationController;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BreadcrumbDto {
    private String path;
    private String label;

    public BreadcrumbDto(String path) {
        setPath(path);
    }

    public BreadcrumbDto setPath(String path) {
        this.path = path;
        return switch (path) {
            case (NavigationController.pathHome) -> {
                label = "Home";
                yield this;
            }
            case (NavigationController.pathNewSeason) -> {
                label = "New Season";
                yield this;
            }
            default -> this;
        };
    }
}
