package net.nuclearprometheus.tpm.applicationserver.config.security

import org.keycloak.representations.adapters.config.PolicyEnforcerConfig

interface PolicyEnforcerPathsProvider {
    val paths: MutableList<PolicyEnforcerConfig.PathConfig>
}
