package com.yusufemrebilgin.boot.jps.client;

import com.yusufemrebilgin.boot.jps.core.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "rawtypes"})
class JsonPlaceHolderClientImplTest {

    @Mock
    RestClient jsonPlaceHolderRestClient;

    @InjectMocks
    JsonPlaceHolderClientImpl jsonPlaceHolderClient;

    @Test
    void shouldListAllUsers() {
        // Arrange
        RestClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(RestClient.RequestHeadersSpec.class);
        RestClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = Mockito.mock(RestClient.ResponseSpec.class);

        List<User> expectedUsers = List.of(
                new User(1, "Jim", "jimhalpert", "jim@example.com", null, null, null, null),
                new User(2, "Dwight", "dwightscrute", "dwight@example.com", null, null, null, null)
        );

        when(jsonPlaceHolderRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/users")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = jsonPlaceHolderClient.listAllUsers();

        // Assert
        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
    }

}