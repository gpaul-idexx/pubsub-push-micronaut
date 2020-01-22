package com.idexx.lims

import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.runtime.Micronaut
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.idexx.lims")
                .mainClass(Application.javaClass)
                .start()
    }
}

@Controller
class PubsubController {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    val messages = mutableListOf<String>()

    @Post("/_ah/push-handlers/trial")
    @Consumes(MediaType.ALL)
    fun trial(@Body body: String) {
        log.info("body: $body")
        messages.add(body)
    }

    @Get("/messages")
    @Produces(MediaType.TEXT_PLAIN)
    fun messages() : String {
        return if (messages.isEmpty()) {
            "no messages"
        } else {
            messages.joinToString(System.lineSeparator())
        }

    }

}

@Controller("/health")
class HealthController() {
    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun check() : String {
        return "{\"status\":\"UP\"}"
    }
}

@Controller("/headers")
class HeaderController() {
    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun check(headers: HttpHeaders) : String {
        val builder = StringBuilder()
        headers.forEach {
            builder.append("${it.key}=${it.value}${System.lineSeparator()}")
        }
        return builder.toString()
    }
}

@Controller("/hello")
class HelloController() {

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        return "hello"
    }
}


