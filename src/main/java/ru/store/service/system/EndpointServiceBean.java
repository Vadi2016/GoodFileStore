package ru.store.service.system;

import org.jetbrains.annotations.NotNull;
import ru.store.api.annotation.Loggable;
import ru.store.api.endpoint.*;
import ru.store.api.system.EndpointService;
import ru.store.api.system.SettingService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EndpointServiceBean implements EndpointService {

    @Inject
    private SettingService settingService;

    @Inject
    private EndpointSettingAPI endpointSettingAPI;

    @Inject
    private EndpointSyncAPI endpointSyncAPI;

    @Inject
    private EndpointApplicationAPI endpointApplicationAPI;

    @Inject
    private EndpointCloudAPI endpointCloudAPI;

    @Inject
    private EndpointStorageAPI endpointStorageAPI;

    @Loggable
    @Override
    public void init() {
        registry(endpointStorageAPI, endpointCloudAPI, endpointSettingAPI, endpointSyncAPI, endpointApplicationAPI);
    }

    private void registry(EndpointAPI... services) {
        @NotNull final List<String> result = new ArrayList<>();
        @NotNull final String baseURL = settingService.getSyncEndpoint();
        for (EndpointAPI service: services) result.add(registry(baseURL, service));
        info(result);
    }

    @NotNull
    private String registry(@NotNull final String baseURL, @NotNull final EndpointAPI api) {
        @NotNull final String serviceURL = baseURL + api.getClass().getSimpleName();
        Endpoint.publish(serviceURL, api);
        return serviceURL;
    }

    @Override
    public boolean start() {
        return false;
    }

    private void info(@NotNull final List<String> urls) {
        if (urls.isEmpty()) return;
        for (String url: urls) System.out.println(url + "?wsdl");
    }
}