package ru.ailabs.kontinuous.controller

import java.util.HashMap
import org.hibernate.Hibernate
import ru.ailabs.kontinuous.persistance.HibernateSession
import org.hibernate.Session

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 30.01.13
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */

public open class Context(val namedParameters : Map<String, String>, val session : Session ) {
}