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

package org.keycloak.jose.jws;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.keycloak.jose.jws.crypto.RSAProvider;
import org.keycloak.jose.jws.crypto.SignatureProvider;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Deprecated
public enum Algorithm {

    none(null, null),
    HS256(AlgorithmType.HMAC, null),
    HS384(AlgorithmType.HMAC, null),
    HS512(AlgorithmType.HMAC, null),
    RS256(AlgorithmType.RSA, new RSAProvider()),
    RS384(AlgorithmType.RSA, new RSAProvider()),
    RS512(AlgorithmType.RSA, new RSAProvider()),
    ES256(AlgorithmType.ECDSA, null),
    ES384(AlgorithmType.ECDSA, null),
    ES512(AlgorithmType.ECDSA, null),
    GOST(AlgorithmType.GOST, null)
    ;

    private static Map<String, Algorithm> namesMap = new HashMap<>(10);

    static {
        namesMap.put("HS256",HS256);
        namesMap.put("HS384",HS384);
        namesMap.put("HS512",HS512);
        namesMap.put("RS256",RS256);
        namesMap.put("RS384",RS384);
        namesMap.put("RS512",RS512);
        namesMap.put("ES256",ES256);
        namesMap.put("ES384",ES384);
        namesMap.put("ES512",ES512);
        namesMap.put("gost34.10-2012",GOST);
    }

    private AlgorithmType type;

    private SignatureProvider provider;

    Algorithm(AlgorithmType type, SignatureProvider provider) {
        this.type = type;
        this.provider = provider;
    }

    public AlgorithmType getType() {
        return type;
    }

    public SignatureProvider getProvider() {
        return provider;
    }


    @JsonCreator
    public static Algorithm forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Entry<String, Algorithm> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null;
    }
}
