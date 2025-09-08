package me.personal.myflix.service.flix.impl;

import me.personal.myflix.entity.flix.Power;
import me.personal.myflix.repository.flix.PowerRepository;
import me.personal.myflix.repository.base.BaseRepository;
import me.personal.myflix.service.base.impl.BaseServiceImpl;
import me.personal.myflix.service.flix.PowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PowerServiceImpl extends BaseServiceImpl<Power, Long> implements PowerService {
    private final PowerRepository powerRepository;

    public PowerServiceImpl(BaseRepository<Power, Long> baseRepository, PowerRepository powerRepository) {
        super(baseRepository, Power.class);
        this.powerRepository = powerRepository;
    }

    @Override
    @Transactional
    public Power patch(Power newPower, Power oldPower) {
        newPower.setName(oldPower.getName());
        newPower.setEffect(oldPower.getEffect());
        return newPower;
    }
}
