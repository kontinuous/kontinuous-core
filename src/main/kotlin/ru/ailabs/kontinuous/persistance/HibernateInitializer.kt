package ru.ailabs.kontinuous

import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.initializer.InitializersBase
import ru.ailabs.kontinuous.persistance.HibernateSession
import ru.ailabs.kontinuous.initializer.Application

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 08.02.13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */

initializers class HibernateInitializer : InitializersBase {
    override fun init(val app: Application) {
        HibernateSession.init(app)
    }
}
