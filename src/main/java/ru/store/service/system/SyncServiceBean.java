package ru.store.service.system;

import ru.store.api.system.SyncService;
import ru.store.api.system.TimerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SyncServiceBean implements SyncService {

    @Inject
    private TimerService timerService;



    @Override
    public boolean status() {
        return timerService.getActive();
    }

    @Override
    public void sync() {

    }

    @Override
    public boolean start() {
        return timerService.start();
    }

    @Override
    public boolean stop() {
        return timerService.stop();
    }
}
