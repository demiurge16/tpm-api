package net.nuclearprometheus.tpm.applicationserver.config.security

import org.keycloak.representations.adapters.config.PolicyEnforcerConfig

fun policyEnforcerConfig(init: PolicyEnforcerConfig.() -> Unit): PolicyEnforcerConfig {
    val config = PolicyEnforcerConfig()
    config.init()
    return config
}

fun pathConfig(init: PolicyEnforcerConfig.PathConfig.() -> Unit): PolicyEnforcerConfig.PathConfig {
    val config = PolicyEnforcerConfig.PathConfig()
    config.init()
    return config
}

fun methodConfig(init: PolicyEnforcerConfig.MethodConfig.() -> Unit): PolicyEnforcerConfig.MethodConfig {
    val config = PolicyEnforcerConfig.MethodConfig()
    config.init()
    return config
}
