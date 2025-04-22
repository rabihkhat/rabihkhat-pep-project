package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    /**
     * Register a new account
     * @return Registered Account
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "INSERT INTO ACCOUNT (username,password) VALUES (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieve all accounts.
     * 
     * @return All accounts given a certain account ID.
     */
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM ACCOUNT";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }  

    // /**
    //  * Retrieve an account by its username.
    //  * @return the Account if found, or null if none exists
    //  */
    // public Account getAccountByUsername(String username) {
    //     String sql = "SELECT * FROM account WHERE username = ?";
    //     try {
    //         Connection conn = ConnectionUtil.getConnection();
    //         PreparedStatement preparedStatement = conn.prepareStatement(sql);
    //         preparedStatement.setString(1, username);
    //         ResultSet rs = preparedStatement.executeQuery();
    //         while(rs.next()){
    //             Account account = new Account(
    //                 rs.getInt("account_id"),
    //                 rs.getString("username"),
    //                 rs.getString("password")
    //             );
    //             return account;
    //         }
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
    //     return null;
    // }

    /**
     * Retrieve an account by its ID.
     * @return the Account if found, or null if none exists
     */
    public Account getAccountById(int id) {
        String sql = "SELECT * FROM ACCOUNT WHERE account_id = ?";
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
