package org.emu.camunda.config.keycloak;


import org.camunda.bpm.extension.keycloak.plugin.KeycloakIdentityProviderPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Copyright 2021-2022 By Dirac Systems.
 *
 * Created by {@khalid.nouh on 21/6/2021}.
 */
@Component
@ConfigurationProperties(prefix="plugin.identity.keycloak")
public class KeycloakIdentityProvider extends KeycloakIdentityProviderPlugin {
}
