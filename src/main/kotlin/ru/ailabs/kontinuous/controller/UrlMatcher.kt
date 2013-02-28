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

class UrlMatcher(val urlTemplate : String) {

    private val logger = LoggerFactory.getLogger(javaClass<UrlMatcher>())

    private class Matching(val paramsPattern: String, val params: List<String>)
    public class Result(val matched: Boolean, val result: Map<String, String>)

    val matching = {
        var matcher = Pattern.compile("""(?<=\/)[\:\*][^\s\/\:]+(?=\/|$)""").matcher(urlTemplate)
        val params = ArrayList<String>()
        var paramsPattern = urlTemplate
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
        Matching(paramsPattern, params)
    } ()

    fun match(url : String) : Result {
        logger.debug("Url: ${url}")
        val result = HashMap<String, String>()
        val matcher = Pattern.compile(matching.paramsPattern).matcher(url)
        val it = matching.params.iterator();
        val matched = matcher.find() && matcher.group().equals(url)
        logger.debug("Matched: ${matched}")
        if(matched){
            var i = 1
            while(i <= matcher.groupCount()) {
                result.put(it.next(), matcher.group(i++)!!)
            }
        }
        result.put("url", url)
        return Result(matched, result)
    }
}
