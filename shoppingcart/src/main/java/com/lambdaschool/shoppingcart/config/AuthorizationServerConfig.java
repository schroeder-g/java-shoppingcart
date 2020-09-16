package com.lambdaschool.shoppingcart.config;

/** Authorization - allows clients to access - username / password => client id / client secret
 Authentication - users - username / password. Restricts specific resources based on roles.
 Access token - identifies the user */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * A.) This class enables and configures the Authorization Server.
 * B.) The class is also responsible for granting authorization to the client.
 * C.) This class is responsible for generating and maintaining the access tokens.
 */

public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
     /** Client Id and Secret are the user name and pw for the client application.
       They are read from the environment variables on your machine.
        Env variables are treated with BIG_CASE by convention. */

    static final String CLIENT_ID = System.getenv("OAUTHCLIENTID");
    static final String CLIENT_SECRET = System.getenv("OAUTHSECRET");
    //generating the password through combination of ID and Secret.
    static final String GRANT_TYPE_PASSWORD = "password";
    //We are using the client id and client security combination to authorize the client.
    // The client id and security can be base64 encoded into a single API key or code.
    static final String AUTHORIZATION_CODE = "authorization_code";

    //Scopes limit what a user can do with the application as a whole.
    //Here we allow the user to read from the application.
    //Currently not implementing scope.
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";

    //tells us how long in sec access code is valid. User logged out after timeout.
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = -1; // always valid

    @Autowired
    private TokenStore tokenStore; //configured in security config. managed by authorization server

    @Autowired
    private AuthenticationManager authenticationManager; // assigns access tokens to users which are managed by auth. server.

    @Autowired
    private PasswordEncoder encoder; //auth tool for encrypting the client secret

/** Method to configure the Client Details Service for our application. This is created and managed by Spring.
 *  We just need to give it our custom configuration.
 *
 * @param configurer The ClientDetailsServiceConfigurer used in our application.
 *                   Spring Boot Security created this for us. We just use it.
 * @throws Exception if the configuration fails */
    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception
    {
        client.inMemory()
                .withClient(CLIENT_ID)
                .secret(encoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
    }
    /** Connects are endpoints to our custom authentication server and token store.
     *  We can also rename the endpoints for certain oauth functions.
     * @param endpoints The Authorization Server Endpoints Configurer is created and managed by Spring Boot Security.
     *                  We give the configurer some custom configuration and let it work!
     * @throws Exception if the configuration fails */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
        endpoints.pathMapping("/oauth/token", "/login");
    }
}
