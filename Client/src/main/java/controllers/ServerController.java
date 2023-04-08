package controllers;

import models.Id;

import javax.json.Json;
import javax.json.JsonString;

public class ServerController {
    private String rootURL = "http://zipcode.rocks:8085";

    private static ServerController serverController = new ServerController();

    private ServerController() {
    }

    public static ServerController shared() {
        return serverController;
    }

    public JsonString idGet() {
        // String for JsonString

        // url -> /ids/
        // send the server a get with url
        // return json from server
        return null;
    }
    public JsonString idPost(Id id) {
        // String for JsonString

        // url -> /ids/
        // create json from Id
        // request
        // reply
        // return json

        return null;
    }
    public JsonString idPut(Id id) {
        // String for JsonString
        // url -> /ids/
        return null;
    }
}

// ServerController.shared.doGet()