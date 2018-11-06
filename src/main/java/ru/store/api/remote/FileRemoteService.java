package ru.store.api.remote;

import ru.store.api.basic.FileService;

import javax.jcr.RepositoryException;

public interface FileRemoteService extends FileService {

    void clearRoot() throws RepositoryException;

    void printListFileNameRoot();
}
