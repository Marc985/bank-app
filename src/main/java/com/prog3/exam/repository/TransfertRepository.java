package com.prog3.exam.repository;

import com.prog3.exam.entity.Transfert;
import com.prog3.exam.entity.TransfertModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransfertRepository {
    @Autowired
Connection connection;
public String saveTransfert(TransfertModal transfert,long senderAccount){
    String sql="insert into transfert values (?,?,?,?,?,?,?)";
    LocalDateTime current_date=LocalDateTime.now();
    String reference="VIR_"+current_date.toString();
    try {
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1,transfert.getReference());
        preparedStatement.setString(2,transfert.getReason());
        preparedStatement.setDouble(3,transfert.getAmount());
        preparedStatement.setDate(4,transfert.getEffecitveDate());
        preparedStatement.setDate(5,transfert.getRegisterDate());
        preparedStatement.setString(6,transfert.getStatus());
        preparedStatement.setLong(7,senderAccount);
        int row=preparedStatement.executeUpdate();

    }catch (Exception e){
        e.printStackTrace();
    }
    return "saved successfully";
}
public String updateStatus(String status,String transfertReference){
    String sql = "update transfert set status=? where reference=? and status='pending'";
    try {
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1,status);
        preparedStatement.setString(2,transfertReference);
        preparedStatement.executeUpdate();
    }catch (Exception e){
        e.printStackTrace();
    }
return null;
}
public List<Transfert> findByEffectiveDate(Date date){
    String sql = "select * from transfert where effective_date=? or effective_date<?";
    List<Transfert> transferts=new ArrayList<>();
    try {
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setDate(1,date);
        preparedStatement.setDate(2,date);
        ResultSet resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            Transfert transfert =new Transfert();

            transfert.setReference(resultSet.getString("reference"));
            transfert.setEffectiveDate(resultSet.getDate("effective_date"));
            transfert.setRegistrationDate(resultSet.getDate("registration_date"));
            transfert.setStatus(resultSet.getString("status"));
            transfert.setAmount(resultSet.getDouble("amount"));
            transfert.setReason(resultSet.getString("reason"));
            transfert.setSenderAccount(resultSet.getLong("account"));
            transferts.add(transfert);

        }

    }catch (Exception e){
        e.printStackTrace();
    }
    return transferts;
}

public  List<Transfert> findALlTransfertByAccount(long accounNumber){
    String sql="select * from transfert where account=? order by effective_date desc";
    List<Transfert> transferts=new ArrayList<>();
    try {
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setLong(1,accounNumber);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            Transfert transfert=new Transfert();
            transfert.setSenderAccount(resultSet.getLong("account"));
            transfert.setStatus(resultSet.getString("status"));
            transfert.setReason(resultSet.getString("reason"));
            transfert.setAmount(resultSet.getDouble("amount"));
            transfert.setReference(resultSet.getString("reference"));
            transfert.setRegistrationDate(resultSet.getDate("registration_date"));
            transfert.setEffectiveDate(resultSet.getDate("effective_date"));
            transferts.add(transfert);
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    return transferts;
}

}
