/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.adapters.springboot;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

public class KeycloakSpringBootConfigResolver implements org.keycloak.adapters.KeycloakConfigResolver {

    private static AdapterConfig adapterConfig;
    private static KeycloakConfigResolver delegateConfigResolver;

    private KeycloakDeployment keycloakDeployment;

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        if (delegateConfigResolver == null) {
            if (keycloakDeployment != null) {
                return keycloakDeployment;
            }

            keycloakDeployment = KeycloakDeploymentBuilder.build(KeycloakSpringBootConfigResolver.adapterConfig);

            return keycloakDeployment;
        }

        return delegateConfigResolver.resolve(request);
    }

    static void setAdapterConfig(AdapterConfig adapterConfig) {
        KeycloakSpringBootConfigResolver.adapterConfig = adapterConfig;
    }

    static void setDelegateConfigResolver(KeycloakConfigResolver configResolver) {
        KeycloakSpringBootConfigResolver.delegateConfigResolver = configResolver;
    }
}