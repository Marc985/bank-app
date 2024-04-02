package com.prog3.exam.repository;

import com.prog3.exam.entity.AccountStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountStatementRepository {
    @Autowired
    Connection connection;
    public List<AccountStatement> getAccountStatement(long accountNumber, Date startDate,Date endDate){
        String sql="select * from account_statement("+accountNumber+",'"+startDate+"','"+endDate+"',null)";
        List<AccountStatement> accountStatements=new ArrayList<>();
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                AccountStatement newAcocuntStatement=new AccountStatement();
                newAcocuntStatement.setReference(resultSet.getString("reference"));
                newAcocuntStatement.setDate(resultSet.getDate("transaction_date"));
                newAcocuntStatement.setSold(resultSet.getDouble("balance"));
                newAcocuntStatement.setReason(resultSet.getString("reason"));

                String type= resultSet.getString("type");
                if(type.equals("debit")){
                    newAcocuntStatement.setDebit(resultSet.getDouble("amount"));
                    newAcocuntStatement.setCredit(0);
                }
                else{
                    newAcocuntStatement.setCredit(resultSet.getDouble("amount"));
                    newAcocuntStatement.setDebit(0);
                }
                accountStatements.add(newAcocuntStatement);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return accountStatements;
    }
    public AccountStatement getByReference(String reference){
        String sql="select * from account_statement(null,null,null"+",'"+reference+"')";

        AccountStatement accountStatement=new AccountStatement();
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){

                accountStatement.setReference(resultSet.getString("reference"));
                accountStatement.setDate(resultSet.getDate("transaction_date"));
                accountStatement.setSold(resultSet.getDouble("balance"));
                accountStatement.setReason(resultSet.getString("reason"));

                String type= resultSet.getString("type");
                if(type.equals("debit")){
                    accountStatement.setDebit(resultSet.getDouble("amount"));
                    accountStatement.setCredit(0);
                }
                else{
                    accountStatement.setCredit(resultSet.getDouble("amount"));
                    accountStatement.setDebit(0);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return accountStatement;
    }
}
