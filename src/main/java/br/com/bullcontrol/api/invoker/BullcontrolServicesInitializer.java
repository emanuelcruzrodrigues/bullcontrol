package br.com.bullcontrol.api.invoker;

import net.sf.cglib.proxy.Enhancer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class BullcontrolServicesInitializer {
    public static void injectResources(Object object){
        injectResources(object, null);
    }

    public static void injectResources(Object object, Class<?> clazz){
        if (clazz == null){
            clazz = object.getClass();
        }
        BullcontrolService resource;
        String serviceName;
        Class<?> serviceClass;
        for (Field field : clazz.getDeclaredFields()) {
            resource = getResource(field);
            if (resource != null){
                serviceName = (resource.name() != null && resource.name().length() > 0) ? resource.name() : field.getName();
                serviceClass = field.getType();
                field.setAccessible(true);

                try {
                    field.set(object, getService(serviceName, serviceClass));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (clazz.getSuperclass() != null){
            injectResources(object, clazz.getSuperclass());
        }
    }

    public static <T> T getService(String serviceName, Class<T> clazz) {
        return (T) Enhancer.create(clazz, new BusinessInterceptor(serviceName, clazz));
    }

    private static BullcontrolService getResource(Field field){
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation instanceof BullcontrolService) return (BullcontrolService) annotation;
        }
        return null;
    }
}
