package ru.store.api.remote;

import ru.store.api.basic.FolderService;

import javax.jcr.RepositoryException;

public interface FolderRemoteService extends FolderService {

    void init();

    void printListFolderNameRoot();

    void clearRoot() throws RepositoryException;
}
