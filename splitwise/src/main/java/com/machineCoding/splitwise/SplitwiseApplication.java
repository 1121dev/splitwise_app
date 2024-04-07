package com.machineCoding.splitwise;

import com.machineCoding.splitwise.commands.CommandExecutor;
import com.machineCoding.splitwise.commands.RegisterUserCommand;
import com.machineCoding.splitwise.commands.SettleUpCommand;
import com.machineCoding.splitwise.commands.UserExpenseCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;
@EnableJpaAuditing
@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {
	@Autowired
	private CommandExecutor commandExecutor;
	@Autowired
	private SettleUpCommand settleUpCommand;
	@Autowired
	private RegisterUserCommand registerUserCommand;
	@Autowired
	private UserExpenseCommand userExpenseCommand;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		commandExecutor.addCommand(settleUpCommand);
		commandExecutor.addCommand(registerUserCommand);
		commandExecutor.addCommand(userExpenseCommand);
		while(true)
		{
			String input = scanner.nextLine();
			commandExecutor.execute(input);
		}
	}
}
