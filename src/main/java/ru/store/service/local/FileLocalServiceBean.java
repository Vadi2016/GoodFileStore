package ru.store.service.local;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.store.api.local.FileLocalService;
import ru.store.api.system.SettingService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class FileLocalServiceBean implements FileLocalService {

    @Inject
    private SettingService settingService;

    @NotNull
    @Override
    public List<String> getListFileNameRoot() {
        final File file = new File(settingService.getSyncFolder());
        final String[] directories = file.list((current, name) -> new File(current, name).isFile());
        if (directories == null) return Collections.emptyList();
        return Arrays.asList(directories);
    }

    @Override
    public void printListFileNameRoot() {
        for (String name: getListFileNameRoot()) System.out.println(name);
    }

    @Override
    public void clearRoot() {
        final File root = new File(settingService.getSyncFolder());
        final List<String> files = getListFileNameRoot();
        for (final String name: files) {
            final File file = new File(root, name);
            file.delete();
        }
    }

    @Nullable
    @Override
    @SneakyThrows
    public byte[] readData(@Nullable final String name) throws IOException {
        if (name == null || name.isEmpty()) return new byte[]{};
        final File file = new File(getRoot(), name);
        return Files.readAllBytes(file.toPath());
    }

    @Override
    @SneakyThrows
    public void writeData(String name, byte[] data) throws IOException {
        if (name == null || name.isEmpty()) return;
        final File file = new File(getRoot(), name);
        final Path path = Paths.get(file.toURI());
        Files.write(path, data);
    }

    @Override
    public boolean exist(String name) {
        if (name == null || name.isEmpty()) return false;
        final File file = new File(getRoot(), name);
        return file.exists();
    }

    @Override
    public void remove(String name) {
        if (name == null || name.isEmpty()) return;
        final File file = new File(getRoot(), name);
        file.delete();
    }

    @Override
    public void createTextFile(String name, String text) throws IOException {
        if (text == null) return;
        writeData(name, text.getBytes());
    }

    @NotNull
    private File getRoot() {
        return new File(settingService.getSyncFolder());
    }
}
