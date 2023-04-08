package youareell;

import controllers.*;

public class YouAreEll {

    TransactionController transactionController;

    public YouAreEll (TransactionController transactionController) {
        this.transactionController = transactionController;
    }

//    public static void main(String[] args) {
//        // hmm: is this Dependency Injection?
//        YouAreEll urlhandler = new YouAreEll(new TransactionController(new MessageController(), new IdController()));
//        System.out.println(urlhandler.MakeURLCall("/ids", "GET", ""));
//        System.out.println(urlhandler.MakeURLCall("/messages", "GET", ""));
//    }

    public String get_ids() {
        return transactionController.makeURLCall("/ids", "GET", "");
    }

    public String get_messages() {
        return transactionController.makeURLCall("/messages", "GET", "");
    }


}
