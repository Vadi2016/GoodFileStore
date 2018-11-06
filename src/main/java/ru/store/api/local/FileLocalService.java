package ru.store.api.local;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.store.api.basic.FileService;

import java.io.IOException;
import java.util.List;

public interface FileLocalService extends FileService {

    @NotNull
    List<String> getListFileNameRoot();

    void clearRoot();

    @Nullable
    @Override
    byte[] readData(String name) throws IOException;
}