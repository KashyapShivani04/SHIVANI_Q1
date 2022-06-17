package com.dummyapi.controller;

import com.dummyapi.service.DummyAPIService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DummyAPIController {

    public static void main (String [] args) {

        ExecutorService service = Executors.newFixedThreadPool(2);

        Runnable createTask = () -> {
            try {
                DummyAPIService.getInstance().createUser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Runnable updateTask = () -> {
            try {
                DummyAPIService.getInstance().updateUser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };


        service.submit(createTask);
        service.submit(updateTask);
        service.shutdown();
    }
}
