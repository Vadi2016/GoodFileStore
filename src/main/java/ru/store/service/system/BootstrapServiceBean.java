package ru.store.service.system;

import ru.store.api.annotation.Loggable;
import ru.store.api.local.FolderLocalService;
import ru.store.api.system.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BootstrapServiceBean implements BootstrapService {

    @Inject
    private ApplicationService applicationService;

    @Inject
    private SettingService settingService;

    @Inject
    private EndpointService endpointService;

    @Inject
    private FolderLocalService folderLocalService;

    @Loggable
    public void init() {
        settingService.init();
        endpointService.init();
        folderLocalService.init();
        applicationService.init();
    }
}
