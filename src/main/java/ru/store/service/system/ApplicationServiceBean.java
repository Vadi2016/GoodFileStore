package ru.store.service.system;


import lombok.SneakyThrows;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;
import org.jboss.weld.environment.se.WeldContainer;
import org.jetbrains.annotations.Nullable;
import ru.store.api.annotation.Loggable;
import ru.store.api.system.ApplicationService;
import ru.store.api.system.SettingService;
import ru.store.api.system.TimerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jcr.*;
import java.net.MalformedURLException;

@ApplicationScoped
public class ApplicationServiceBean implements ApplicationService {

    @Inject
    private SettingService settingService;

    @Inject
    private TimerService timerService;

    @Inject
    private WeldContainer weldContainer;

    private Repository repository = null;

    private Session session = null;

    private Exception error = null;

    @Loggable
    public void init() {
        if (settingService.getSyncActive()) timerService.start();
        if (settingService.getJcrActive()) login();
    }

    @Override
    public void shutdown() {
        weldContainer.shutdown();
        System.exit(0);
    }

    @Loggable
    @Override
    public boolean login() {
        if (status()) return false;
        try {
            final String jsrUrl = settingService.getJcrUrl();
            repository = new URLRemoteRepository(jsrUrl);
            final String jsrLogin = settingService.getJcrLogin();
            final String jsrPass = settingService.getJcrPassword();
            final char[] pass = jsrPass.toCharArray();
            final SimpleCredentials credentials = new SimpleCredentials(jsrLogin, pass);
            session = repository.login(credentials);
            return true;
        } catch (final MalformedURLException | RepositoryException e) {
            error = e;
            return false;
        }
    }

    public boolean logout() {
        if (repository == null) return false;
        if (session == null) return false;
        try {
            session.logout();
            repository = null;
            session = null;
            return true;
        } catch (Exception e) {
            error = e;
            return false;
        }

    }

    @Override
    public boolean status() {
        return repository != null && session != null;
    }


    @Nullable
    @Override
    public Exception error() {
        return error;
    }

    @Loggable
    @Override
    public Repository repository() {
        return repository;
    }

    public Session session() {
        return session;
    }

    @Loggable
    @Override
    @SneakyThrows
    public Node getRootNode() throws RepositoryException {
        if (!status()) return null;
        return session.getRootNode();
    }

    @Override
    @SneakyThrows
    public boolean save() throws RepositoryException {
        if (!status()) return false;
        session.save();
        return true;
    }
}
