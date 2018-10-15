package ru.store.service.system;

import ru.store.api.annotation.Loggable;
import ru.store.api.system.SettingService;
import ru.store.api.system.SyncTask;
import ru.store.api.system.TimerService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.util.Timer;

@ApplicationScoped
public class TimerServiceBean implements TimerService {

    @Inject
    private SettingService settingService;

    private final Timer timer = new Timer();

    private SyncTask task = null;


    @Override
    public boolean getActive() {
        return task != null;
    }

    @Override
    public void setActive(boolean active) {
        if (active) start();
        else stop();
    }

    @Loggable
    @Override
    public synchronized boolean start() {
        if (task != null) return false;
        final Integer timeout = settingService.getSyncTimeout();
        task = CDI.current().select(SyncTask.class).get();
        timer.schedule(task.get(), 0, timeout);
        return true;
    }

    @Override
    public synchronized boolean stop() {
        if (task == null) return false;
        task.cancel();
        task = null;
        return true;
    }

    @Override
    public void restart() {
        stop();
        start();
    }
}
