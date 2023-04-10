package controllers;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import models.Id;
import models.Message;

public class MessageController {

    private HashSet<Message> messagesSeen;
    ArrayList<Message> msg;
    // why a HashSet??
    public MessageController() {
        this.messagesSeen = new HashSet<>();
    }

    public ArrayList<Message> getMessages() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://zipcode.rocks:8085/messages"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        //functions that would match every endpoint (messages, ids)
        //once json is sent to endpoint, have to catch it and return it back
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return parse(result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Message> parse (String a) {
        JSONArray myObject = new JSONArray(a);
        Iterator<Object> it = myObject.iterator();
        msg = new ArrayList<>();
        while (it.hasNext()) {
            JSONObject json = new JSONObject(it.next().toString());
            String message = json.getString("message");
            String id = json.getString("fromid");
            String toId = json.getString("toid");
            msg.add(new Message(message, id, toId));
        }
        return msg;
    }


    public ArrayList<Message> getMessagesForId(Id idFor) {
        String urlString = String.format(
                "http://zipcode.rocks:8085/ids/%s/messages", idFor.getGithub());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return parse(result);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
        return null;
    }


    public Message getMessageForSequence(String seq, Id myID) {
        String urlString = String.format(
                "http://zipcode.rocks:8085/ids/%s/messages/%s" , myID.getGithub(), seq);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            String from = json.getString("fromid");
            String to = json.getString("toid");
            String mesg = json.getString("message");
            return new Message(mesg, from, to);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        String urlString = String.format(
                "http://zipcode.rocks:8085/ids/%s/from/%s", myId.getGithub(), friendId.getGithub());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return parse(result);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
        return null;

    }

    public Message postMessage(Id myId, Id toId, Message msg){
        String jsonString = String.format("{\"sequence\" : \"%s\",\"timestamp\" : \"%s\",\"fromid\" : \"%s\",\"toid\" : \"%s\",\"message\" : \"%s\"}", msg.getSeqId(), msg.getTimestamp(), myId.getGithub(), toId.getGithub(), msg.getMessage());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://zipcode.rocks:8085/ids/" + myId.getGithub() + "/messages"))
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response;
        try {

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            String from = json.getString("fromid");
            String to = json.getString("toid");
            String mesg = json.getString("message");
            Message result = new Message(mesg, from, to);
            return result;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}