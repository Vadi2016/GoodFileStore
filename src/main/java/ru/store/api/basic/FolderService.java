package ru.store.api.basic;

import org.jetbrains.annotations.NotNull;

import javax.jcr.RepositoryException;
import java.util.List;

public interface FolderService {

    void printListFolderNameRoot();

    @NotNull
    List<String> getListFolderNameRoot();

    void createFolder(String folderName) throws RepositoryException;

    void clearRoot() throws RepositoryException;

    void removeFolder(String folderName) throws RepositoryException;


}
