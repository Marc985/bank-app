package com.prog3.exam.repository;

import com.prog3.exam.entity.Account;
import com.prog3.exam.entity.Sold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class AccountRepository extends Request<Account> {
    @Autowired
    Connection connection;

    public AccountRepository(Connection connection) {
        super(connection);
    }
    @Autowired
    SoldRepository soldRepository;

    @Override
    public Account save(Account account) {

        String sql="insert into bank_account(account_number,client_name,client_last_name,birthdate," +
                "monthly_net_income,is_eligible) values (?,?,?,?,?,?,?)";
        int isCreated=0;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1,account.getAccountNumber());
            preparedStatement.setString(2,account.getClientName());
            preparedStatement.setString(3,account.getClientLastName());
            preparedStatement.setTimestamp(4,account.getBirthdate());
            preparedStatement.setDouble(5,account.getMonthlyNetIncome());
            preparedStatement.setBoolean(6,false);
            preparedStatement.setString(7,account.getUser_id());
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

    @Override
    public List<Account> findAll() {
        return super.findAll();
    }


    public Account updateAccount(Account toUpdate) {
        String clientName = toUpdate.getClientName();
        String lastName = toUpdate.getClientLastName();
        Date birthdate = toUpdate.getBirthdate();
        double monthlyNetIncome = toUpdate.getMonthlyNetIncome();
        String sql = "update bank_account set client_name='" + clientName + "'," +
                " client_last_name='" + lastName + "', birthdate='" + birthdate + "'," +
                " monthly_net_income='" + monthlyNetIncome + "'" + "where user_id = " + toUpdate.getUser_id() + "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int resultSet = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return toUpdate;
    }

    public Account findAccountById(long idAccount) {
        String sql="select * from bank_account where account_number="+idAccount;
        Account account=null;
        String accountNumberColumn="account_number";
        String  clientNameColumn="client_name";
        String clientLastNameColumn="client_last_name";
        String birthdateColumn="birthdate";
        String monthlyNetColumn="monthly_net_income";
        String isEligibleColumn="is_eligible";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
             account=new Account(
                     resultSet.getLong(accountNumberColumn),
                     resultSet.getString(clientNameColumn),
                     resultSet.getString(clientLastNameColumn),
                     resultSet.getTimestamp(birthdateColumn),
                     resultSet.getDouble(monthlyNetColumn),
                     resultSet.getBoolean(isEligibleColumn),
                     resultSet.getString("user_id")
             );

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }

    public String updateEligibility(float idAccount, boolean option){
        String sql="update bank_account set is_eligible="+option;
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
        return "bank_account";
    }
}
