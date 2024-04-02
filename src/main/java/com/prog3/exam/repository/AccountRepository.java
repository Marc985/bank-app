package com.prog3.exam.repository;

import com.prog3.exam.entity.Account;
import com.prog3.exam.entity.Sold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class AccountRepository extends Request<Account> {
    @Autowired
    Connection connection;

    public AccountRepository(Connection connection) {
        super(connection);
    }
    @Autowired
    SoldRepository soldRepository;



    String accountNameColumn="account_name";
    String accountNumberColumn="account_number";
    String idClientColumn="id_client";
    String isEligibleColumn="is_eligible";


    public Account save(Account account) {



        String sql="insert into account(account_number,is_eligible,id_client,account_name) values (?,?,?,?)";
        int isCreated=0;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1,account.getAccountNumber());
            preparedStatement.setBoolean(2,false);
            preparedStatement.setString(3, account.getIdClient());
            preparedStatement.setString(4,account.getAccountName());


            isCreated=preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(isCreated>0){
            LocalDate date=LocalDate.now();
            Sold initalSold=new Sold();
            initalSold.setAccountId(account.getAccountNumber());
            initalSold.setBalance(0);
            initalSold.setDate(java.sql.Date.valueOf(date));
            soldRepository.save(initalSold);



        }
        return  account;
    }

    public List<Account> findAllByIdClient(String idClient) {
        String sql="select * from account where id_client=?";
        List<Account> accounts=new ArrayList<>();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,idClient);
            ResultSet resultSet=preparedStatement.executeQuery();


            while (resultSet.next()){
                Account account=new Account();
                account.setAccountNumber(resultSet.getLong(accountNumberColumn));
                account.setAccountName(resultSet.getString(accountNameColumn));
                account.setIdClient(resultSet.getString(idClientColumn));
                account.setEligible(resultSet.getBoolean(isEligibleColumn));
                accounts.add(account);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  accounts;
    }




    public Account findAccountById(long idAccount) {
        String sql="select * from account where account_number="+idAccount;
        Account account=new Account();

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){

                account.setAccountNumber(resultSet.getLong(accountNumberColumn));
                account.setAccountName(resultSet.getString(accountNameColumn));
                account.setIdClient(resultSet.getString(idClientColumn));
                account.setEligible(resultSet.getBoolean(isEligibleColumn));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }

    public String updateEligibility(long idAccount, boolean option){
        String sql="update account set is_eligible="+option+" where account_number="+idAccount;
        String message="error while trying to update";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
           int response= preparedStatement.executeUpdate();
           if (response!=0){
               message="this account is now eligble to make withdrawal";
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String getTableName() {
        return "account";
    }
}
