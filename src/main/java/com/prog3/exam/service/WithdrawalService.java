package com.prog3.exam.service;

import com.prog3.exam.entity.Account;
import com.prog3.exam.entity.Client;
import com.prog3.exam.entity.Sold;
import com.prog3.exam.entity.Transaction;
import com.prog3.exam.repository.AccountRepository;
import com.prog3.exam.repository.ClientRepository;
import com.prog3.exam.repository.SoldRepository;
import com.prog3.exam.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class WithdrawalService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SoldRepository soldRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;
    public String makeWithdrawal(long idAccount,String reason, double amount,int category){
        Client client=clientRepository.findByIdAccount(idAccount);
        Account account = accountRepository.findAccountById(idAccount);
        Sold sold = soldRepository.findLastSoldByIdAccount(idAccount);

        if (!account.getIsEligible()&&sold.getBalance()<amount) {
            return "This account is not eligible to make withdrawal";
        }

       // Loan lastLoan = loanRepository.getLastLoan(idAccount);
        double actualSold = sold.getBalance();

        if (actualSold<0) {
            return "You should pay back your last loan";
        }



        double allowedCredit = client.getMonthlyNetSalary() / 3;
        if ((allowedCredit + actualSold) < amount) {
            return "The allowed credit + your actual sold don't cover the withdrawal";
        }

    processWithDrawal(idAccount,actualSold,amount,reason,category);
        return "success";
    }
    private void processWithDrawal(long idAccount,double actualSold,double amount,String reason,int category){
        LocalDate currentDate=LocalDate.now();
      updateSold(Date.valueOf(currentDate),idAccount,(actualSold-amount));
      addTransaction(amount,reason,Date.valueOf(currentDate),idAccount,category);
    }

     private void updateSold(Date date,long idAccount,double value){
         Sold newSold=new Sold();

         newSold.setDate(date);
         newSold.setAccountId(idAccount);
         newSold.setBalance(value);
         soldRepository.save(newSold);



     }
    private void addTransaction(double amount,String reason,Date date,long accountNumber,int category){
        Transaction newTransaction=new Transaction();
        newTransaction.setType("debit");
        newTransaction.setAmount(amount);
        newTransaction.setReason(reason);
        newTransaction.setDate(date);
        newTransaction.setAccountNumber(accountNumber);
        newTransaction.setCategory(category);
        transactionRepository.saveTransaction(newTransaction);
    }

}
