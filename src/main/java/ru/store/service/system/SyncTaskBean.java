package ru.store.service.system;

import ru.store.api.system.SyncService;
import ru.store.api.system.SyncTask;

import javax.inject.Inject;
import java.util.TimerTask;


public class SyncTaskBean extends TimerTask implements SyncTask {

    @Inject
    private SyncService syncService;

    @Override
    public TimerTask get() {
        return this;
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public void run() {
        syncService.sync();
    }
}
