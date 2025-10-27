package com.yusufemrebilgin.boot.jps.client;

import com.yusufemrebilgin.boot.jps.core.JsonPlaceHolderClient;
import com.yusufemrebilgin.boot.jps.core.JsonPlaceHolderClientException;
import com.yusufemrebilgin.boot.jps.core.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.List;

public class JsonPlaceHolderClientImpl implements JsonPlaceHolderClient {

    private static final ParameterizedTypeReference<List<User>> USER_LIST_TYPE_REF = new ParameterizedTypeReference<>() {};

    private final RestClient jsonPlaceHolderRestClient;

    public JsonPlaceHolderClientImpl(RestClient jsonPlaceHolderRestClient) {
        this.jsonPlaceHolderRestClient = jsonPlaceHolderRestClient;
    }

    @Override
    public List<User> listAllUsers() {
        return jsonPlaceHolderRestClient.get()
                .uri("/users")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new JsonPlaceHolderClientException("Client error " + response.getStatusCode());
                })
                .body(USER_LIST_TYPE_REF);
    }

}
