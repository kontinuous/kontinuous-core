package ru.ailabs.kontinuous.annotation;

import org.reflections.Reflections;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationUtils {
    public static Set<Class<?>> findRoutesClasses() {
        Reflections r = new Reflections("");
        return r.getTypesAnnotatedWith(routes.class);
    }
}
