package com.lux.wso2.stream;

import com.lux.wso2.exceptions.StreamException;
import com.lux.wso2.stream.spec.WMBEventsStreamDefinitionBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Igor on 02.04.2014.
 */
public class StreamDefinitionBuilderFactory {

    private final static Map<String, Class> namedBuilderRegistry = new HashMap<>();

    private final static ReadWriteLock rwLock = new ReentrantReadWriteLock();

    static {
        rwLock.writeLock().lock();
        namedBuilderRegistry.put("WMBEvent", WMBEventsStreamDefinitionBuilder.class);
        rwLock.writeLock().unlock();
    }

    public synchronized static Set<String> getRegistredFactorySet() {
        rwLock.readLock().lock();
        Set<String> registredBuilderNames = Collections.unmodifiableSet(namedBuilderRegistry.keySet());
        rwLock.readLock().unlock();
        return registredBuilderNames;
    }

    public static synchronized void registerNewFactory(String name, Class<StreamDefinitionBuilder> implementation) {
        rwLock.writeLock().lock();
        namedBuilderRegistry.put(name, implementation);
        rwLock.writeLock().unlock();
    }

    /**
     * Create new StreamDefinitionBuilder with specific class
     * @param clazz - implementation for StreamDefinitionBuilder.
     * @return StreamDefinitionBuilder instance.
     * @throws StreamException
     */
    public static StreamDefinitionBuilder createFor(Class clazz) throws StreamException {
        return createFor(clazz.getName());
    }

    /**
     * Create new StreamDefinitionBuilder with specific name or fully qualified class name.
     * @param builderName - registred builder names or fully qualified class name (using Class.forName).
     * @return StreamDefinitionBuilder instance.
     * @throws StreamException
     */
    public static StreamDefinitionBuilder createFor(final String builderName) throws StreamException {
        if (namedBuilderRegistry.containsKey(builderName)) {
            rwLock.readLock().lock();
            Class implementation = namedBuilderRegistry.get(builderName);
            rwLock.readLock().unlock();
            return createInstanceByClass(implementation);
        } else {
            return createInstanceByClassName(builderName);
        }
    }

    private static StreamDefinitionBuilder createInstanceByClass(Class clazz) throws StreamException {
        try {
            return (StreamDefinitionBuilder) clazz.newInstance();
        } catch (Exception e) {
            throw new StreamException("Stream definition builder with class name [" + clazz.getName() + "] not registered. " + e.getMessage(), e);
        }
    }

    private static StreamDefinitionBuilder createInstanceByClassName(String className) throws StreamException {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new StreamException("Can't find class " + className + " for StreamDefinitionBuilder. ", e);
        }
        return createInstanceByClass(clazz);
    }
}
