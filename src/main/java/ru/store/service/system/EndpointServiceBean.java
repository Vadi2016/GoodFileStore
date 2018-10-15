package ru.store.service.system;

import org.jetbrains.annotations.NotNull;
import ru.store.api.annotation.Loggable;
import ru.store.api.system.EndpointService;
import ru.store.api.system.SettingService;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

public class EndpointServiceBean implements EndpointService {

    @Inject
    private SettingService settingService;

    @Inject
    private EndpointSettingsApi endpointSettingsApi;

    @Inject
    private EndpointSyncApi endpointSyncApi;

    @Inject
    private EndpointApplicationApi endpointApplicationApi;

    @Inject
    private EndpointCloudApi endpointCloudApi;

    @Inject
    private EndpointStorageApi endpointStorageApi;

    @Loggable
    @Override
    public void init() {
        registry(endpointStorageApi, endpointCloudApi, endpointSettingsApi, endpointSyncApi, endpointApplicationApi);
    }

    private void registry(EndpointApi... services) {
        @NotNull final List<String> result = new ArrayList<>();
        @NotNull final String baseUrl = settingService.getSyncEndpoint();
        for (EndpointApi service : services) result.add(registry(baseUrl, service));
        info(result);
    }

    private String registry(@NotNull final String baseUrl, @NotNull final EndpointApi api) {
        @NotNull final String serviceUrl = baseUrl + api.getClass.getSimpleName();
        Endpoint.publish(serviceUrl, api);
        return serviceUrl;

    }

    private void info(@NotNull final List<String> urls) {
        if (urls.isEmpty()) return;
        for (String url: urls) System.out.println(url+"?wsdi");
    }

}
