package ru.store.api.system;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface SettingService {

    void init() throws IOException;

    @NotNull
    Boolean getJcrActive();

    @NotNull
    String getJcrUrl();

    @NotNull
    String getJcrLogin();

    @NotNull
    String getJcrPassword();

    @NotNull
    String getSyncFolder();

    @NotNull
    Integer getSyncTimeout();

    @NotNull
    String getSyncEndpoint();

    @NotNull
    Boolean getSyncActive();
}
