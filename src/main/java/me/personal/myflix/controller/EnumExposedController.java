package me.personal.myflix.controller;

import me.personal.myflix.config.framework.annotation_resolver.EnumExposedService;
import me.personal.myflix.dto.EnumExposedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EnumExposedController {

    @Autowired
    private EnumExposedService service;

    @GetMapping("/enum/{enumName}")
    public EnumExposedResult getEnum(@PathVariable("enumName") String enumName) {
        return this.service.getEnumExposedResult(enumName);
    }
}
