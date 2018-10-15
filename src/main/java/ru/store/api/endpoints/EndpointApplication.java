package ru.store.api.endpoints;

import ru.store.dto.ResultDTO;

public interface EndpointApplication {

    public ResultDTO ping();

    public ResultDTO shutdown();

    public ResultDTO connected();

    public ResultDTO login();

    public ResultDTO logout();

    public ResultDTO status();

    public ResultDTO sync();

    public ResultDTO start();

    public ResultDTO stop();
}
