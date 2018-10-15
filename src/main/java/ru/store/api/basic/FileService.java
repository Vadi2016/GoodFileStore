package ru.store.api.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.List;

public interface FileService {

    @NotNull
    List<String> getListFileNameRoot();

    void printListFileNameRoot();

    void clearRoot() throws RepositoryException;

    @Nullable
    byte[] readData(String name) throws IOException, RepositoryException;

    void writeData(String name, byte[] data) throws IOException, RepositoryException;

    boolean exist(String name) throws RepositoryException;

    void remove(String name) throws RepositoryException;

    void createTextFile(String name, String text) throws IOException, RepositoryException;
}
