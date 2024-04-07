package com.machineCoding.splitwise.commands;

import com.machineCoding.splitwise.controllers.SettleUpController;
import com.machineCoding.splitwise.dto.SettleUpUserRequestDto;
import com.machineCoding.splitwise.dto.SettleUpUserResponseDto;
import com.machineCoding.splitwise.services.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SettleUpCommand implements Command{
    @Autowired
    private SettleUpController settleUpController;
    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));

        Long userId = Long.valueOf(words.get(0));

        SettleUpUserRequestDto request = new SettleUpUserRequestDto();
        request.setUserId(userId);

        SettleUpUserResponseDto responseDto =
                settleUpController.settleUpUser(request);

        List<Transaction> transactions = responseDto.getTransactions();
        Integer toGive = 0, toRecieve = 0;
        for(Transaction transaction : transactions)
        {
            if(transaction.getFrom().getId()==userId)
            toGive+=transaction.getAmount();

            if(transaction.getTo().getId()==userId)
                toRecieve+=transaction.getAmount();
            System.out.println(transaction.getFrom().getName()+" -> "+transaction.getTo().getName()+" : "+transaction.getAmount());
        }
        System.out.println("Your transactions tally");
        System.out.println("Total amount to give = "+toGive);
        System.out.println("TOtal amount to recieve = "+toRecieve);
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 2 &&
                words.get(1).equalsIgnoreCase(CommandKeywords.settleUp);
    }
}
