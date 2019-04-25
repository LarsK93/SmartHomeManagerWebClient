import dao.TemperatureValveDao
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document
import kotlin.browser.window

val button = document.getElementById("button_id") as HTMLButtonElement

fun main() {
    window.onload = {
        //Example of how to add stylesheets dynamically
        //add stylesheet if we have any
        val head = document.getElementsByTagName("head")
        head[0]?.appendChild(createStylesheetLink("style.css"))
        //bind click listener on button
        button.addEventListener("click", fun(_: Event) {
            sendTemperature()
        })
        fetchTemperatureValve()
    }
}

fun sendTemperature() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun fetchTemperatureValve() {
    val url = "http://localhost:8080/heating/targettemperaturevalve"
    val req = XMLHttpRequest()
    val temperatureInputEl = document.getElementById("temperature") as HTMLInputElement
    val valveInputEl = document.getElementById("valve") as HTMLInputElement
    req.onloadend = fun(_: Event){
        if (req.status == HttpStatus.OK.code) {
            val text = req.responseText
            println(text)
            val temperatureValveDao = JSON.parse<TemperatureValveDao>(text)
            temperatureInputEl.value = temperatureValveDao.targetTemperature.toString()
            valveInputEl.value = temperatureValveDao.valve.toString()
            temperatureInputEl.disabled = false
            button.disabled = false
        }
        else {
            println("Request returned unexpected status code ${req.status}")
        }
    }
    req.open("GET", url, true)
    req.send()
}

fun createStylesheetLink(filePath: String): Element {
    val style = document.createElement("link")
    style.setAttribute("rel", "stylesheet")
    style.setAttribute("href", filePath)
    return style
}

external fun alert(message: Any?)