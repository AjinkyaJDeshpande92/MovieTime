package com.careem.movietime.utilities;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;

/**
 * This class is used to customise the implementation of the Otto bus.
 * <p>
 * The Register/Unregister methods are overriden in this class for error handling.
 *
 * @author AjinkyaD
 * @version 1.0
 */

public class DataBus extends Bus {

    public DataBus(ThreadEnforcer threadEnforcer) {
        super(threadEnforcer);
    }

    private ArrayList registeredObjects = new ArrayList<>();

    @Override
    public void register(Object object) {
        removeBusListeners();
        if (!registeredObjects.contains(object)) {
            registeredObjects.add(object);
            super.register(object);
        }
    }

    @Override
    public void unregister(Object object) {
        if (registeredObjects.contains(object)) {
            registeredObjects.remove(object);
            super.unregister(object);
        }
    }

    public void removeBusListeners() {
        if (registeredObjects != null) {

            for (Object currentObject :
                    registeredObjects) {
                super.unregister(currentObject);
            }

            registeredObjects.clear();
        }
    }
}
