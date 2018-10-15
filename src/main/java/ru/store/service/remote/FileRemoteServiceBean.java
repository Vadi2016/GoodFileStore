package ru.store.service.remote;

import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.store.api.remote.FileRemoteService;
import ru.store.api.system.ApplicationService;

import javax.inject.Inject;
import javax.jcr.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FileRemoteServiceBean implements FileRemoteService {

    @Inject
    private ApplicationService applicationService;


    @NotNull
    @Override
    public List<String> getListFileNameRoot() {
        final List<String> result = new ArrayList<>();
        try {
            final Node root = applicationService.getRootNode();
            if (root == null) return Collections.emptyList();
            final NodeIterator nt = root.getNodes();
            while (nt.hasNext()) {
                final Node node = nt.nextNode();
                final NodeType nodeType = (NodeType) node.getPrimaryNodeType();
                final boolean isFile = ((javax.jcr.nodetype.NodeType) nodeType).isNodeType("nt:file");
                if (isFile) result.add(node.getName());
            }
            return result;
        } catch (RepositoryException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void printListFileNameRoot() {
        for (final String name : getListFileNameRoot()) System.out.println(name);
    }

    @Override
    @SneakyThrows
    public void clearRoot() throws RepositoryException {
        final Node root = applicationService.getRootNode();
        final NodeIterator nt = root.getNodes();
        while (nt.hasNext()) {
            final Node node = nt.nextNode();
            final NodeType nodeType = (NodeType) node.getPrimaryNodeType();
            final boolean isFolder = ((javax.jcr.nodetype.NodeType) nodeType).isNodeType("nt:folder");
            if (isFolder) node.remove();
        }
    }

    @Nullable
    @Override
    @SneakyThrows
    public  byte[] readData(String name) throws RepositoryException, IOException {
        if (name == null || name.isEmpty()) return new byte[]{};
        final Node root = applicationService.getRootNode();
        if (root == null) return new byte[]{};
        final Node node = root.getNode(name);
        final Binary binary = node.getProperty("jcr:data").getBinary();
        return IOUtils.toByteArray(binary.getStream());
    }

    @Override
    @SneakyThrows
    public void writeData(@Nullable final String name, byte[] data) throws RepositoryException {
        if (name == null || name.isEmpty()) return;
        final Session session = applicationService.session();
        if (session == null) return;
        final Node root = applicationService.getRootNode();
        final Node file = root.addNode(name, "nt:file");
        final Node contentNode = file.addNode("jcr:content", "nt:resource");
        final ByteArrayInputStream stream = new ByteArrayInputStream(data);
        final Binary binary = session.getValueFactory().createBinary(stream);
        contentNode.setProperty("jcr:data", binary);
        final Calendar created = Calendar.getInstance();
        contentNode.setProperty("jcr:lastModified", created);
    }

    public boolean exist(String name) throws RepositoryException {
        if (name == null || name.isEmpty()) return false;
        final Node root = applicationService.getRootNode();
        if (root == null) return false;
        return root.hasNode(name);
    }

    @Override
    @SneakyThrows
    public void remove(@Nullable final String name) throws RepositoryException {
        if (name == null || name.isEmpty()) return;
        final Node root = applicationService.getRootNode();
        if (root == null) return;
        final Node node = root.getNode(name);
        node.remove();
    }

    @Override
    @SneakyThrows
    public void createTextFile(@Nullable final String name,@Nullable final String text) throws RepositoryException {
        if (text == null) return;
        writeData(name, text.getBytes());
    }
}
