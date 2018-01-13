package com.xqh.pattern.partterns.abstractDocument;

import com.xqh.pattern.partterns.abstractDocument.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class App {

    private static final Logger LOGGER= LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        Map<String,Object> carProperties=new HashMap<>();
        carProperties.put(HasModel.PROPERTY,"tesla");
        carProperties.put(HasPrice.PROPERTY,123);

        Map<String,Object> wheelProperties=new HashMap<>();
        wheelProperties.put(HasModel.PROPERTY,"米其林轮胎");
        wheelProperties.put(HasPrice.PROPERTY,500L);
        wheelProperties.put(HasType.PROPERTY,"超耐用");

        Map<String,Object> doorProperties=new HashMap<>();
        doorProperties.put(HasModel.PROPERTY,"safe door");
        doorProperties.put(HasPrice.PROPERTY,500L);
        doorProperties.put(HasType.PROPERTY,"好好好");

        carProperties.put(HasParts.PROPERTY, Arrays.asList(wheelProperties,doorProperties));

        Cars car=new Cars(carProperties);

        LOGGER.info("Here is our car:");
        LOGGER.info("-> model: {}", car.getModel().get());
        LOGGER.info("-> price: {}", car.getPrice().get());
        LOGGER.info("-> parts: ");
        car.getParts().forEach(p -> LOGGER.info("{}/{}/{}", p.getType().get(), p.getModel().get(), p.getPrice().get()));

    }
}
