package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {


    /**
     * Creates a message
     * 
     * @return created Message.
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "INSERT INTO MESSAGE (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }   

    // public Message insertMessage(Message message){
    //     String sql = "INSERT INTO MESSAGE (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
    //     try (Connection conn = ConnectionUtil.getConnection();
    //          PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
    //         ps.setInt   (1, message.getPosted_by());
    //         ps.setString(2, message.getMessage_text());
    //         ps.setLong  (3, message.getTime_posted_epoch());
    
    //         ps.executeUpdate();
    
    //         // pull back the auto-generated PK
    //         try (ResultSet keys = ps.getGeneratedKeys()) {
    //             if (keys.next()) {
    //                 int id = keys.getInt(1);
    //                 // either set it on the existing object...
    //                 // message.setMessage_id(id);
    //                 // …or create a fresh one:
    //                 message = new Message(id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
    //             }
    //         }
    
    //         return message;
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //         return null;
    //     }
    // }

    /**
     * Retrieve all messages.
     * 
     * @return All messages given a certain message ID.
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM MESSAGE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }  
    
    /**
     * Retrieve a message given a certain message ID.
     * 
     * @return Message given a certain message ID.
     */
    public Message getMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM MESSAGE WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }   

    /**
     * Delete a message given a certain message ID.
     * 
     * @return deleted Message given a certain message ID.
     */
    public Message deleteMessageByID(int id){
        Message message = getMessageByID(id);
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "DELETE FROM MESSAGE WHERE MESSAGE_ID = (?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    } 

    /**
     * update a message given a certain message ID.
     * 
     * @return updated Message given a certain message ID.
     */
    public Message updateMessageByID(Message message, String newMessageText){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE MESSAGE SET MESSAGE_TEXT = (?) WHERE MESSAGE_ID = (?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1,newMessageText);
            preparedStatement.setInt(2,message.getMessage_id());
            preparedStatement.executeUpdate();
            return new Message(message.getMessage_id(), message.getPosted_by(),newMessageText, message.getTime_posted_epoch());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    } 

    /**
     * Retrieve all messages given a certain acount ID.
     * 
     * @return All messages given a certain account ID.
     */
    public List<Message> getAllMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM MESSAGE WHERE POSTED_BY = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }   
}
