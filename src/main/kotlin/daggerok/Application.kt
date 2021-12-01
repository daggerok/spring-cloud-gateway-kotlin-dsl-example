package daggerok

import io.netty.handler.codec.http.HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.support.beans

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        addInitializers(
            beans {
                bean {
                    ref<RouteLocatorBuilder>().routes {
                        route {
                            path("/*")
                            filters {
                                addResponseHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                                rewritePath("/(?<segment>.*)", "/?q=\${segment}")
                            }
                            uri("http://www.google.com")
                        }
                    }
                }
            }
        )
    }
}
