/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.java;

import care.data4life.sdk.Data4LifeClient;
import care.data4life.sdk.network.Environment;

public class Main {

    public static void main(String[] args) {
        Data4LifeClient client = Data4LifeClient.init(
                "alias",
                "clientId",
                "clientSecret",
                Environment.PRODUCTION,
                "redirectUrl",
                "platform"
        );

        String authorizationUrl = client.getAuthorizationUrl();
    }
}
