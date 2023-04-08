package controllers;

import models.Id;

import java.util.List;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085/ids";
    private MessageController msgCtrl;
    private IdController idCtrl;

    public TransactionController(MessageController msgController, IdController idController) {
        msgCtrl = msgController;
        idCtrl = idController;
    }

    public List<Id> getIds() {
        return null;
    }
    public String postId(String idToRegister, String githubName) {
        Id tid = new Id(idToRegister, githubName);
        tid = idCtrl.postId(tid);
        return ("Id registered.");
    }

    // CREATED THIS METHOD
    // httpMethod =>  GET POST PUT

    public String makecall(String endPath, String httpMethod, String msg) {
        return null;
    }
}
