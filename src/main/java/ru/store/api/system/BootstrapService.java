package ru.store.api.system;

import java.io.IOException;

public interface BootstrapService {

    void init() throws IOException;

    void cleanupAfterBoot();

    void cleanup();
}
