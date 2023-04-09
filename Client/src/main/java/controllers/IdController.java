package controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import models.Id;
import org.json.JSONArray;
import org.json.JSONObject;

public class IdController {
    private String rootURL = "http://zipcode.rocks:8085/ids";

    private HashMap<String, Id> allIds; // githubID , Id

    Id myId;

    public ArrayList<Id> getIds() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootURL))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return parseJSONtoIDs(result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Id> parseJSONtoIDs(String data){
        JSONArray myObject = new JSONArray(data);
        Iterator<Object> it = myObject.iterator();
        ArrayList<Id> ids = new ArrayList<>();
        while(it.hasNext()) {
            JSONObject json = new JSONObject(it.next().toString());
            String name = json.getString("name");
            String id = json.getString("userid");
            String github = json.getString("github");
            ids.add(new Id(id,name,github));
        }
        return ids;
    }

    public Id postId(Id id) {
        String jsonString = String.format("{\"userid\" : \"%s\",\"name\" : \"%s\",\"github\" : \"%s\"}",
                "-",id.getName(),id.getGithub());
        //JSONObject json = new JSONObject(jsonString);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootURL))
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            return new Id(json.getString("userid"),json.getString("name"),json.getString("github"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Id putId(Id id) {
        return null;
    }

}