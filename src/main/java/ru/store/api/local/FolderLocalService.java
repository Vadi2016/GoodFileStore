package ru.store.api.local;

import ru.store.api.basic.FolderService;

public interface FolderLocalService extends FolderService {

    void init();

    void clearRoot();

    void printListFolderNameRoot();
}
