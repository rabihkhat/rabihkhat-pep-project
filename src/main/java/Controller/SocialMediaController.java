package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}",this::patchMessageByIDHandler);
        app.get("/accounts/{account_id}/messages",this::getAllMessagesByAccountHandler);
        return app;
    }

    /**
     * This is a register handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registered = accountService.registerAccount(account);
        if (registered != null) {
            context.json(mapper.writeValueAsString(registered));
        } else {
            context.status(400);
        }        
    }

    /**
     * This is a login handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account login = mapper.readValue(context.body(), Account.class);
        Account loggedIn = accountService.loginAccount(login);
        if (loggedIn != null) {
            context.json(mapper.writeValueAsString(loggedIn));
        } else {
            context.status(401);
        }     
    }

    /**
     * This is a handler to create a message for a post endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            context.json(addedMessage);
        } else {
            context.status(400);
        }     
    }

    /**
     * This is a handler to retreive all messages.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * This is a handler to retreive a message by ID.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIDHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.result("");
        }
    }

    /**
     * This is a handler to remove a message by ID.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIDHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByID(messageId);
        if (deletedMessage != null) {
            context.json(deletedMessage);
        } else {
            context.result("");
        }
    }

    /**
     * This is a handler to update a message by ID.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchMessageByIDHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        message.setMessage_id(Integer.parseInt(context.pathParam("message_id")));
        Message patchedMessage = messageService.patchMessageByID(message);
        if (patchedMessage != null) {
            context.json(patchedMessage);  
        } else {
            context.status(400);
        }
    }

    /**
     * This is a handler to get all message by Account ID.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */    
    private void getAllMessagesByAccountHandler(Context context) throws JsonProcessingException{
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getAllMessagesByAccount(accountId));
    }
}