package com.demo.authen.services;

import com.demo.authen.auth.KeycloakProvider;
import com.demo.authen.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyCloakService {
    private final KeycloakProvider kcProvider;

    @Value("http://localhost:8080")
    String keycloakUrl;

    @Value("realm_01")
    String keycloakRealm;

    @Value("myclient")
    String keycloakClientId;

//    private static final String CLIENT_ID = "client_id";
//    private static final String USERNAME = "username";
//    private static final String PASSWORD = "password";
//    private static final String GRANT_TYPE = "grant_type";
//    private static final String URL = "url";
//
////    private static final String REQUESTED_SUBJECT = "requested_subject";
////    private static final String REFRESH_TOKEN = "refresh_token";
//
//    public KeyCloakResponse getAccessTokenFromKeycloak(String username, String password) {
//        var keyCloakRequest = new HashMap<String, String>();
//        keyCloakRequest.put(CLIENT_ID, keycloakClientId);
//        keyCloakRequest.put(USERNAME, username.toLowerCase());
//        keyCloakRequest.put(PASSWORD, password);
//        keyCloakRequest.put(GRANT_TYPE, OAuth2Constants.PASSWORD);
//        keyCloakRequest.put(GRANT_TYPE, OAuth2Constants.PASSWORD);
//        keyCloakRequest.put(URL, keycloakUrl);
//        var response = keyCloakClient.getAccessToken(keycloakRealm, keyCloakRequest);
//        return response.getBody();
//    }
//
//    public UserInfoResponse getUserInfo(String realm, String token){
//        return null;
//    }

    public Response createKeycloakUser(CreateUserRequest user) {
        UsersResource usersResource = kcProvider.getInstance().realm(keycloakRealm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = usersResource.create(kcUser);

        if (response.getStatus() == 201) {
            //If you want to save the user to your other database, do it here, for example:
//            User localUser = new User();
//            localUser.setFirstName(kcUser.getFirstName());
//            localUser.setLastName(kcUser.getLastName());
//            localUser.setEmail(user.getEmail());
//            localUser.setCreatedDate(Timestamp.from(Instant.now()));
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//            usersResource.get(userId).sendVerifyEmail();
//            userRepository.save(localUser);
        }

        return response;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
