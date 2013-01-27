/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

Retention(RetentionPolicy.RUNTIME) annotation class routes
Retention(RetentionPolicy.RUNTIME) annotation class path(val path: String)
