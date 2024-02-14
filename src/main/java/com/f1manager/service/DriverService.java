package com.f1manager.service;

import com.f1manager.model.dao.DriverDao;
import com.f1manager.model.dto.DriverDto;
import com.f1manager.model.dto.SearchResultDto;
import com.f1manager.model.exception.NoResultsException;
import com.f1manager.model.exception.NoValueException;
import com.f1manager.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final ConfigurationService configurationService;
    private final FragmentService fragmentService;

    public void validate(DriverDto driverDto) throws NoValueException {
        if (StringUtils.isEmpty(driverDto.getFirstname()) || StringUtils.isEmpty(driverDto.getLastname())) {
            throw new NoValueException("Names must be defined");
        }
        if (driverDto.getImageBase64() == null || !driverDto.getImageBase64().startsWith("data:image/png;base64,")) {
            driverDto.setImageBase64(configurationService.getDiverAvatar());
        }
    }

    public List<DriverDto> findAll() {
        return driverRepository
            .findAll()
            .stream()
            .map(this::daoDtoConverter)
            .toList();
    }

    public DriverDto findById(String id) {
        return driverRepository
            .findById(id)
            .map(this::daoDtoConverter)
            .orElse(null);
    }

    public String findHtmlById(String id) throws NoResultsException {
        DriverDto driver = findById(id);
        if (driver != null) {
            return driver.getHtml();
        }
        throw new NoResultsException("Nothing found with the id: " + id);
    }

    public List<DriverDto> findByName(String name) {
        return driverRepository
            .findByName(name)
            .stream()
            .map(this::daoDtoConverter)
            .toList();
    }

    public String findHtmlByName(String name) throws NoResultsException {
        List<DriverDto> drivers = findByName(name);
        if (drivers.isEmpty()) {
            throw new NoResultsException("Nothing found with the name: " + name);
        }
        List<SearchResultDto> results = drivers
            .stream()
            .map(driver -> new SearchResultDto(
                driver.getId(),
                driver.getFirstname() + " " + driver.getLastname()
            )).toList();
        return fragmentService.render("searchResultView", Map.of("results", results));
    }

    public DriverDto save(DriverDto driverDto) {
        return daoDtoConverter(
            driverRepository.save(dtoDaoConverter(driverDto))
        );
    }

    public DriverDao dtoDaoConverter(DriverDto driverDto) {
        return new DriverDao()
            .setId(driverDto.getId())
            .setFirstname(driverDto.getFirstname())
            .setLastname(driverDto.getLastname())
            .setImageBase64(driverDto.getImageBase64());
    }

    public DriverDto daoDtoConverter(DriverDao driverDao) {
        return buildHtml(new DriverDto()
            .setId(driverDao.getId())
            .setFirstname(driverDao.getFirstname())
            .setLastname(driverDao.getLastname())
            .setImageBase64(driverDao.getImageBase64())
        );
    }

    private DriverDto buildHtml(DriverDto driverDto) {
        return driverDto.setHtml(fragmentService.render(
            "driverView", Map.of("driver", driverDto)
        ));
    }
}
