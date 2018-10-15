package ru.volnenko.custom;

import ru.volnenko.custom.service.*;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

/**
 * @author Denis Volnenko
 */
public class App {

    public static void main(String[] args) {
        final SeContainer container = SeContainerInitializer.newInstance().addPackages(App.class).initialize();
        final GlobalService globalService = CDI.current().select(GlobalService.class).get();
        globalService.run();
    }

}
