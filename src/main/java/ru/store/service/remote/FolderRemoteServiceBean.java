package ru.store.service.remote;

import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.store.api.remote.FolderRemoteService;
import ru.store.api.system.ApplicationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FolderRemoteServiceBean implements FolderRemoteService {

    @Inject
    private ApplicationService applicationService;

    @Override
    public void init() {

    }

    @Override
    public void printListFolderNameRoot() {
        for (final String name : getListFolderNameRoot()) System.out.println(name);
    }

    @NotNull
    public List<String> getListFolderNameRoot() {
        final List<String> result = new ArrayList<>();
        try {
            final Node root = applicationService.getRootNode();
            final NodeIterator nt = root.getNodes();
            while (nt.hasNext()) {
                final Node node = nt.nextNode();
                final NodeType nodeType = (NodeType) node.getPrimaryNodeType();
                final boolean isFolder = ((javax.jcr.nodetype.NodeType) nodeType).isNodeType("nt:folder");
                if (isFolder) result.add(node.getName());
            }
            return result;
        } catch (RepositoryException e) {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    @SneakyThrows
    public void createFolder(String folderName) throws RepositoryException {
        @Nullable final Node root = applicationService.getRootNode();
        if (root == null) return;
        root.addNode(folderName, "nt:folder");
        applicationService.save();
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

    @Override
    @SneakyThrows
    public void removeFolder(@Nullable final String folderName) throws RepositoryException {
        if (folderName == null || folderName.isEmpty()) return;
        final Node root = applicationService.getRootNode();
        final Node node = root.getNode(folderName);
        node.remove();
    }

}
