package ru.ailabs.kontinuous.controller

import java.util.regex.Pattern
import java.util.ArrayList
import java.util.HashMap

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 30.01.13
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */

class UrlMatcher(val urlTemplate : String) {

    fun match(val url : String) : Pair<Boolean, Map<String, String> > {
        val result = HashMap<String, String>()

        var matcher = Pattern.compile("""(?<=\/)\:[^\s\/\:]+(?=\/|$)""").matcher(urlTemplate)
        val params = ArrayList<String>()
        while (matcher.find()) {
            params.add(matcher.group().substring(1))
        }
        val paramsPattern = matcher.replaceAll("""([^\\s\/\:]+)""")
        matcher = Pattern.compile(paramsPattern).matcher(url)
        val it = params.iterator();
        val matched = matcher.find() && matcher.group().equals(url)
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
