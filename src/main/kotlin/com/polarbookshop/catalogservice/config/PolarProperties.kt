package com.polarbookshop.catalogservice.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "polar")
class PolarProperties {
    /**
     * A message to welcome users.
     */
    var greeting: String = ""
}
