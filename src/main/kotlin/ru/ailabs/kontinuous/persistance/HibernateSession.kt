package ru.ailabs.kontinuous.persistance

import java.util.Properties
import org.hibernate.HibernateException
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.logger.LoggerFactory
import org.hibernate.Session

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 08.02.13
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */

class HibernateSessionInstance(factory: SessionFactory) {
    val session = factory.openSession()!!
    val tx = session.beginTransaction()!!;
}

object HibernateSession {

    private val logger = LoggerFactory.getLogger("ru.ailabs.kontinuous.persistance.HibernateSession")

    private val DEFAULT_DB_DRIVER = "org.h2.Driver"
    private val DEFAULT_DB_URL = "jdbc:h2:mem:kontinuous"

    var sessionFactory: SessionFactory? = null

    fun init(app: Application) {
        logger.info("Hibernate initialize start")
        val defaultProperties = Properties()
        defaultProperties.setProperty("hibernate.connection.driver_class", DEFAULT_DB_DRIVER)
        defaultProperties.setProperty("hibernate.connection.url", DEFAULT_DB_URL)

        var config = Configuration()
                .mergeProperties(app.properties)!!
                .mergeProperties(defaultProperties)

        try {
            config = config!!.configure()
        } catch (e : HibernateException) {
            logger.warn(e.getMessage(), e)
        } finally {
            val factory = try {
                config!!.buildSessionFactory()
            } catch (e : Exception) {
                logger.error(e.getMessage(), e)
                null
            }
            println(factory)
            // FIXME: workaround, you can not use stack with try-catch, see http://youtrack.jetbrains.com/issue/KT-3309
            sessionFactory = factory
        }
        logger.info("Hibernate initialized")
    }

    fun initialized() : Boolean {
        return sessionFactory != null
    }

    fun wrap<T>(func: HibernateSessionInstance.() -> T) : T {
        val inst = HibernateSessionInstance(sessionFactory!!)
        try{
            return inst.func()
        } finally {
            inst.tx.commit();
            inst.session.close();
        }
    }
}