package ru.store.service.system;

import ru.store.api.annotation.Loggable;
import ru.store.api.local.FolderLocalService;
import ru.store.api.system.*;
import ru.store.events.keyboard.KeyboardInitEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class BootstrapServiceBean implements BootstrapService {

    @Inject
    private BootstrapService bootstrapService;

    @Inject
    private ApplicationService applicationService;

    @Inject
    private SettingService settingService;

    @Inject
    private EndpointService endpointService;

    @Inject
    private FolderLocalService folderLocalService;

    @Inject
    private Event<KeyboardInitEvent> keyboardInputInitEvent;

    @Loggable
    public void init() throws IOException {
        bootstrapService.init();
        settingService.init();
        endpointService.init();
        folderLocalService.init();
        applicationService.init();
        keyboardInputInitEvent.fire(new KeyboardInitEvent());
    }

    @Override
    public void cleanupAfterBoot() {
        bootstrapService.hashCode();
        bootstrapService.cleanup();
    }

    @Override
    public void cleanup() {
        bootstrapService.hashCode();
        bootstrapService.cleanupAfterBoot();
    }
}