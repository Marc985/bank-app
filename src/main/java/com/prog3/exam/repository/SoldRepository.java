package com.prog3.exam.repository;

import com.prog3.exam.entity.Sold;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SoldRepository extends Request<Sold>{

    public SoldRepository(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return "sold";
    }
    public Sold findSoldByDate(long idAccount, Date date){
        String sql="SELECT * FROM sold WHERE account_id=? AND date<? OR date=? ORDER BY id_sold DESC limit 1";

        Sold sold=new Sold();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1,idAccount);
            preparedStatement.setDate(2,date);
            preparedStatement.setDate(3,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                sold.setBalance( resultSet.getDouble("balance"));
                sold.setDate(resultSet.getDate("date"));
                sold.setAccountId(resultSet.getLong("account_id"));


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  sold;
    }
    @Override
    public Sold save(Sold toSave){
        return super.save(toSave);
    }
    public Sold findLastSoldByIdAccount(float idAccount){
        String sql="select * from sold where account_id="+idAccount+"" +
                " order by id_sold desc limit 1";
        Sold sold=null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
               sold= new Sold(
                        resultSet.getDouble("balance"),
                        resultSet.getDate("date"),
                       resultSet.getLong("account_id")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  sold;
    }


}
