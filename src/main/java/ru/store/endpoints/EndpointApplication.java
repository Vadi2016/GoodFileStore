package ru.store.endpoints;

import ru.store.api.system.ApplicationService;
import ru.store.api.system.SyncService;
import ru.store.dto.ResultDTO;
import ru.store.dto.SuccessDTO;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class EndpointApplication implements ru.store.api.endpoints.EndpointApplication {

    @Inject
    private ApplicationService applicationService;

    @Inject
    private SyncService syncService;

    @Override
    @WebMethod
    public ResultDTO ping() {
        return new SuccessDTO();
    }

    @Override
    @WebMethod
    public ResultDTO shutdown() {
        applicationService.shutdown();
        return new SuccessDTO();
    }

    @Override
    @WebMethod
    public ResultDTO connected() {
        return new ResultDTO(applicationService.status());
    }

    @Override
    @WebMethod
    public ResultDTO login() {
        return new ResultDTO(applicationService.login());
    }

    @Override
    @WebMethod
    public ResultDTO logout() {
        return new ResultDTO(applicationService.logout());
    }

    @Override
    @WebMethod
    public ResultDTO status() {
        return new ResultDTO(syncService.status());
    }

    @Override
    @WebMethod
    public ResultDTO sync() {
        syncService.sync();
        return new SuccessDTO();
    }

    @Override
    @WebMethod
    public ResultDTO start() {
        return new ResultDTO(syncService.start());
    }

    @Override
    @WebMethod
    public ResultDTO stop() {
        return new ResultDTO(syncService.stop());
    }
}
