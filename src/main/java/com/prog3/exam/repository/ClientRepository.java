package com.prog3.exam.repository;

import com.prog3.exam.entity.Client;
import org.springframework.stereotype.Repository;

import javax.naming.ldap.PagedResultsControl;
import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class ClientRepository extends  Request<Client> {
    String idClientColumn="id_client";
    String firstNameColumn="first_name";
    String lastNameColumn="last_name";
    String birthdateColumn="birthdate";
    String monthlySalaryColumn="monthly_net_salary";

    public ClientRepository(Connection connection) {
        super(connection);
    }
    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    public Client findById(String id){
        String sql="select * from client where id_client=?";
        Client client=new Client();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                client.setIdClient(resultSet.getString(idClientColumn));
                client.setFirstName(resultSet.getString(firstNameColumn));
                client.setLastName(resultSet.getString(lastNameColumn));
                client.setBirthdate(resultSet.getDate(birthdateColumn));
                client.setMonthlyNetSalary(resultSet.getDouble(monthlySalaryColumn));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return client;
    }
    public Client findByIdAccount(long idAccount){
        String sql="select client.id_client,first_name,last_name,birthdate,monthly_net_salary  from client inner " +
                "join account on client.id_client=account.id_client where account_number=?";
        Client client=new Client();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1,idAccount);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                client.setIdClient(resultSet.getString(idClientColumn));
                client.setFirstName(resultSet.getString(firstNameColumn));
                client.setLastName(resultSet.getString(lastNameColumn));
                client.setMonthlyNetSalary(resultSet.getDouble(monthlySalaryColumn));
                client.setBirthdate(resultSet.getDate(birthdateColumn));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return client;

    }
    public Client updateClientById(String idClient,Client client){
       String sql= "UPDATE client SET first_name = ?, last_name = ?, monthly_net_salary = ? WHERE id_client = ?";
       try {
           PreparedStatement preparedStatement=connection.prepareStatement(sql);
           preparedStatement.setString(1,client.getFirstName());
           preparedStatement.setString(2,client.getLastName());
           preparedStatement.setDouble(3,client.getMonthlyNetSalary());
           preparedStatement.setString(4,idClient);
           int result=preparedStatement.executeUpdate();


       }catch (Exception e){
           e.printStackTrace();
       }
       return client;
    }
    @Override
    public Client save(Client client){
        return super.save(client);
    }
    @Override
    public String getTableName() {
        return "client";
    }
}
