package com.prog3.exam.repository;

import com.prog3.exam.ConnectionDB;
import com.prog3.exam.entity.Account;
import com.prog3.exam.entity.Sold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AccountCrudOperation extends Request<Account> {
    @Autowired
    Connection connection;

    public AccountCrudOperation(Connection connection) {
        super(connection);
    }
    @Autowired
    SoldCrudOperation soldCrudOperation;
    @Override
    public Account save(Account entity) {
        Account account= super.save(entity);
        if(account !=null){
            LocalDate date=LocalDate.now();
            Sold initalSold=new Sold();
            initalSold.setIdSold(9);
            initalSold.setAccountId(account.getAccountNumber());
            initalSold.setLoans(0);
            initalSold.setLoansinterest(2);
            initalSold.setDate(java.sql.Date.valueOf(date));
            soldCrudOperation.save(initalSold);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        return super.findAll();
    }


    public Account updateAccount(Account toUpdate) {
        String clientName = toUpdate.getClientName();
        String lastName = toUpdate.getClientLastName();
        Date birthdate = toUpdate.getBirthdate();
        double monthlyNetIncome = toUpdate.getMonthlyNetIncome();
        String sql = "update account set client_name='" + clientName + "'," +
                " client_last_name='" + lastName + "', birthdate='" + birthdate + "'," +
                " monthly_net_income='" + monthlyNetIncome + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int resultSet = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return toUpdate;
    }

    public Account findAccountById(float idAccount) {
        String sql="select * from account where account_number="+idAccount;
        Account account=null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
             account=new Account(
                     resultSet.getLong("account_number"),
                     resultSet.getString("client_name"),
                     resultSet.getString("client_last_name"),
                     resultSet.getDate("birthdate"),
                     resultSet.getDouble("monthly_net_income")
             );

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public String getTableName() {
        return "account";
    }
}