package ru.store;

import ru.store.service.system.BootstrapServiceBean;

import javax.enterprise.inject.se.SeContainerInitializer;

public class App {
    public static void main(String[] args) {
        SeContainerInitializer.newInstance().addPackages(App.class).initialize()
                .select(BootstrapServiceBean.class).get().init();
    }
}
