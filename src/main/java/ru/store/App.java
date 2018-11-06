package ru.store;

import ru.store.api.system.BootstrapService;

import javax.enterprise.inject.se.SeContainerInitializer;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            SeContainerInitializer.newInstance().addPackages(App.class).initialize()
                    .select(BootstrapService.class).get().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
