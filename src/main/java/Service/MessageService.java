package Service;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;

import java.util.List;

/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 *
 * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
 * application that follows best practices will often have unnecessary code, but this makes the code more
 * readable and maintainable in the long run!
 */
public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO and AccountDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO and AccountDAO.
     * There is no need to modify this constructor.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;

    }   

    /**
     * @param message a message object.
     * @return The persisted message if the persistence is successful, null if it was unsuccessfully persisted.
     */
    public Message addMessage(Message message) {
        String text = message.getMessage_text();
        int user = message.getPosted_by();

        if (text == null || text.trim().isEmpty() || text.length() > 255) {
            return null;
        } else if (accountDAO.getAccountById(user) == null) {
            return null;
        } else {
        return messageDAO.insertMessage(message);
        }    
    }

    /**
     * @param message a message object.
     * @return The persisted list of messages if the persistence is successful, null if it was unsuccessfully persisted.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();

    }

    /**
     * @param message a message object.
     * @return The persisted message if the persistence is successful, null if it was unsuccessfully persisted.
     */
    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id);

    }

    /**
     * @param message a message object.
     * @return The persisted message if the persistence is successful, null if it was unsuccessfully persisted.
     */
    public Message deleteMessageByID(int id) {
        Message toDelete = messageDAO.getMessageByID(id);
        if (toDelete == null) {
            return null;
        } else {
            return messageDAO.deleteMessageByID(id);
        }
        
    }

    /**
     * @param message a message object.
     * @return The persisted message if the persistence is successful, null if it was unsuccessfully persisted.
     */
    public Message patchMessageByID(Message message) {
        Message toUpdate = messageDAO.getMessageByID(message.getMessage_id());
        String text = message.getMessage_text();
        if (toUpdate == null) {
            return null;
        } else if (text == null || text.isEmpty() || text.length() > 255) {
            return null;
        } else {
            Message updated = messageDAO.updateMessageByID(toUpdate, text);
            return updated;
        }
    }

    /**
     * @param message a message object.
     * @return The persisted list of messages if the persistence is successful, null if it was unsuccessfully persisted.
     */
    public List<Message> getAllMessagesByAccount(int id) {       
        return messageDAO.getAllMessageByID(id);

    }
    
}
