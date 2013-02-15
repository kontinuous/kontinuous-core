package ru.ailabs.kontinuous.persistance

import ru.ailabs.kontinuous.initializer.InitializersBase
import ru.ailabs.kontinuous.initializer.Application
import org.hibernate.cfg.Configuration
import java.util.Properties
import org.hibernate.SessionFactory
import ru.ailabs.kontinuous.logger.LoggerFactory
import org.hibernate.HibernateException

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 08.02.13
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */

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
                .mergeProperties(defaultProperties)!!
                .mergeProperties(app.properties)

        try {
            config = config!!.configure()
        } catch (val e : HibernateException) {
            logger.warn(e.getMessage(), e)
        } finally {
            val factory = try {
                config!!.buildSessionFactory()
            } catch (val e : Exception) {
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
}