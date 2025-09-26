package me.personal.myflix.controller.flix;

import me.personal.myflix.controller.base.BaseController;
import me.personal.myflix.entity.flix.Power;
import me.personal.myflix.service.base.BaseService;
import me.personal.myflix.service.flix.PowerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/power")
public class PowerController extends BaseController<Power, Long> {

    private final PowerService powerService;

    public PowerController(BaseService<Power, Long> baseService, PowerService powerService) {
        super(baseService);
        this.powerService = powerService;
    }
}
