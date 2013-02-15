package ru.ailabs.kontinuous.annotation

import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.ClasspathHelper

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 13.02.13
 * Time: 9:53
 */
open class AnnotationScanner {
    open fun scan(annotationClass: java.lang.Class<out jet.Annotation>): Set<java.lang.Class<out Any?>> {
        return Reflections( ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())).getTypesAnnotatedWith(annotationClass)!!
    }

    fun scanFor(annotationClass: java.lang.Class<out jet.Annotation>, process: (java.lang.Class<out Any?>) -> Unit) {
        for (cls in scan(annotationClass).toCollection()) {
            process(cls)
        }
    }
}
