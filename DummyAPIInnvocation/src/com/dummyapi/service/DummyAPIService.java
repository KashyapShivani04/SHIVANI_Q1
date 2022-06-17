package com.dummyapi.service;

import com.dummyapi.model.User;
import com.dummyapi.repository.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class DummyAPIService {
    private static volatile DummyAPIService dummyAPIService = null;
    private static final Object lock = new Object();

    public static DummyAPIService getInstance() {

        if(dummyAPIService == null ) {

            synchronized (lock) {
                if(dummyAPIService == null ) {
                    dummyAPIService = new DummyAPIService();
                }
            }
        }
        return dummyAPIService;

    }

    public void createUser() throws Exception {

        //Fetch the UserData
        URLConnection connection = new URL("https://dummyapi.io/data/v1/user?page=1&limit=10").openConnection();
        connection.setRequestProperty("app-id", "62aae6c295a625da065b3a18");

        //Get Response
        InputStream inputStream = connection.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");

        //Fetch the User Json
        String result = s.hasNext() ? s.next() : "";

        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the User Json into the User Pojo
        User user = objectMapper.readValue(result, User.class);

        //Insert the User Data into the DB
        UserRepository.getInstance().insertUser(user);

    }

    public void updateUser() throws Exception {

        //Fetch the UserData
        URLConnection connection = new URL("https://dummyapi.io/data/v1/user?page=1&limit=10").openConnection();
        connection.setRequestProperty("app-id", "62aae6c295a625da065b3a18");

        //Get Response
        InputStream inputStream = connection.getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");

        //Fetch the User Json
        String result = s.hasNext() ? s.next() : "";

        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        // Convert the User Json into the User Pojo
        User user = objectMapper.readValue(result, User.class);

        //Updates the User Data into the DB
        UserRepository.getInstance().updateUser(user);

    }
}