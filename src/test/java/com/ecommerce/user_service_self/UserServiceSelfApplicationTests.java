package com.ecommerce.user_service_self;

import com.ecommerce.user_service_self.security.repository.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.annotation.Commit;

import java.util.UUID;

//@SpringBootTest
class UserServiceSelfApplicationTests {
//	@Autowired
	private JpaRegisteredClientRepository jpaRegisteredClientRepository;

//	@Test
//	void contextLoads() {
//	}

	// we have created this testcase to store the registered client into the database using test
	//we use @commit to persist the data into the database
//	@Test
//	@Commit
//	void storeRegisteredClientIntoDB(){
//		RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("oidc-client")
//                .clientSecret("{noop}secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback") //redirect to PostMan
//                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/callback")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("ADMIN")
//                .scope("MENTEE")
//                .scope("MENTOR")
//                .scope("INSTRUCTOR")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//		jpaRegisteredClientRepository.save(oidcClient);
//	}

}
