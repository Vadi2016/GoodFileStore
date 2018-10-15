package ru.store.api.system;

public interface SyncService {

    boolean status();

    void sync();

    boolean start();

    boolean stop();
}
