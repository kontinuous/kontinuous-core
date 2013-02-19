package ru.ailabs.kontinuous.controller

import java.util.regex.Pattern
import java.util.ArrayList
import java.util.HashMap
import ru.ailabs.kontinuous.logger.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 30.01.13
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */

//todo need to generate regexp for url at the constructor
class UrlMatcher(val urlTemplate : String) {

    val logger = LoggerFactory.getLogger(javaClass<UrlMatcher>())

    //todo need to replace Pair with a Result object. It will be more clearly
    fun match(val url : String) : Pair<Boolean, Map<String, String> > {
        val result = HashMap<String, String>()

        var matcher = Pattern.compile("""(?<=\/)[\:\*][^\s\/\:]+(?=\/|$)""").matcher(urlTemplate)
        val params = ArrayList<String>()
        var paramsPattern = urlTemplate
        logger.debug("Url: ${url}")
        logger.debug("Url template: ${urlTemplate}")
        while (matcher.find()) {
            val param = matcher.group()
            logger.debug("Find parameter: ${param}")
            params.add(param.substring(1))
            if(param.startsWith(":"))
                paramsPattern = paramsPattern.replace(param, """([^\/]+)""")
            if(param.startsWith("*"))
                paramsPattern = paramsPattern.replace(param, """(.*)""")
        }
        logger.debug("Url pattern: ${paramsPattern}")
//        val paramsPattern = matcher.replaceAll("""([^\\s\/\:]+)""")
        matcher = Pattern.compile(paramsPattern).matcher(url)
        val it = params.iterator();
        val matched = matcher.find() && matcher.group().equals(url)
        logger.debug("Matched: ${matched}")
        if(matched){
            var i = 1
            while(i <= matcher.groupCount()) {
                result.put(it.next(), matcher.group(i++)!!)
            }
        }
        result.put("url", url)
        return Pair(matched, result)
    }
}
