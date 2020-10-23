package com.users.example;

import com.users.example.enums.State;
import com.users.example.gen.model.Address;
import com.users.example.gen.model.UpdateUserRequest;
import com.users.example.gen.model.UsersResponse;
import com.users.example.gen.model.UsersResponseData;
import com.users.example.persistent.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;

import java.nio.charset.Charset;

public class BaseTest {


    public Pair<String,String> getAdminValidCredentials(){
        return Pair.of("admin","password");
    }

    public Pair<String,String> getUserValidCredentials(){
        return Pair.of("user","password1");
    }

    public Pair<String,String> getInValidCredentials(){
        return Pair.of("admin","password566");
    }

    public UpdateUserRequest buildUpdateUserRequest(){
        UpdateUserRequest request = new UpdateUserRequest();

        request.setTitle("Mrs");
        request.setFirstName("Vidya");
        request.setLastName("Jadhav");
        request.setGender("Female");

        Address address = new Address();
        address.setCity("Sydney");
        address.setStreet("45 Lillian");
        address.setPostcode("2145");
        address.setState(State.NSW.name());
        request.setAddress(address);
        return request;
    }

    public UpdateUserRequest buildInvalidUpdateUserRequest(){
        UpdateUserRequest request = new UpdateUserRequest();

        request.setTitle("Mrss");
        request.setFirstName("Vidya");
        request.setLastName("Jadhav");
        request.setGender("Females");

        Address address = new Address();
        address.setCity("Sydney");
        address.setStreet("45 Lillian");
        address.setPostcode("2145");
        address.setState("");
        request.setAddress(address);
        return request;
    }


    public UsersResponse buildUserResponse(){
        UsersResponse response = new UsersResponse();

        UsersResponseData data = new UsersResponseData();

        data.setId(Long.valueOf(1));
        data.setTitle("Mrs");
        data.setFirstName("Vidya");
        data.setLastName("Jadhav");
        data.setGender("Female");

        Address address = new Address();
        address.setCity("Sydney");
        address.setStreet("45 Lillian");
        address.setPostcode("2145");
        address.setState(State.NSW.name());
        data.setAddress(address);

        response.setData(data);
        return response;
    }

    public User buildUser(){
        User user = new User();

        user.setId(Long.valueOf(1));
        user.setTitle("Mrs");
        user.setFirstName("Vidya");
        user.setLastName("Jadhav");
        user.setGender("Female");
        user.setCity("Sydney");
        user.setStreet("45 Lillian");
        user.setPostcode("2145");
        user.setState(State.NSW.name());

        return user;
    }


    public HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
