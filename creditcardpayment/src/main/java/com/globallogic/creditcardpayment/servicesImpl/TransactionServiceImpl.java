package com.globallogic.creditcardpayment.servicesImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Account;
import com.globallogic.creditcardpayment.entity.CreditCard;
import com.globallogic.creditcardpayment.entity.Customer;
import com.globallogic.creditcardpayment.entity.Statement;
import com.globallogic.creditcardpayment.entity.Transaction;
import com.globallogic.creditcardpayment.pdfAndEmailService.EmailSenderService;
import com.globallogic.creditcardpayment.pdfAndEmailService.PdfGenerator;
import com.globallogic.creditcardpayment.repositories.AccountRepo;
import com.globallogic.creditcardpayment.repositories.CreditCardRepo;
import com.globallogic.creditcardpayment.repositories.CustomerRepo;
import com.globallogic.creditcardpayment.repositories.StatementRepo;
import com.globallogic.creditcardpayment.repositories.TransactionRepo;
import com.globallogic.creditcardpayment.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepo transactionRepo;

	@Autowired
	StatementRepo statementRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	CreditCardRepo creditCardRepo;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	PdfGenerator pdfGenerator;

	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public List<Transaction> showTransaction() {
		return transactionRepo.findAll();
	}

	@Override
	public Transaction showTransactionDetails(long id) {
		return transactionRepo.findById(id).get();
	}

	@Override
	public String addTransaction(Transaction transaction) {
		Customer customer = customerRepo.findById(transaction.getCustomer().getId()).get();
		transaction.setCustomer(customer);

		// current date from system
		transaction.setTranDate(java.time.LocalDate.now());

		// set card number from credit card details of customer
		CreditCard creditcard = creditCardRepo.findById(customer.getCreditCard().getId()).get();
		transaction.setCardNumber(creditcard.getCardNumber());

		// update balance in account table after transaction
		Account account = accountRepo.findById(customer.getAccount().getAccountid()).get();
		if (account.getBalance() > transaction.getAmount()) {
			double updateBalance = account.getBalance() - transaction.getAmount();
			accountRepo.setBalanceForAccount(updateBalance, account.getAccountid());
			transaction.setStatus("SUCCESSFULL");
			transactionRepo.save(transaction);

			// Generating pdf for transaction details
			String path = pdfGenerator.transactionForCustomer(transaction.getTransid(), updateBalance);

			// sending mail to customer for successfull transaction in pdf with all
			// transaction details
			try {
				emailSenderService.sendEmailWithAttachment(customer.getEmail(),
						"Transaction details attached with mail", "TRANSACTION SUCCESSFULL " + customer.getName(),
						path);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "TRANSACTION SUCCESSFULL - MAIL SENT FOR TRANSACTION ";
		}
		transaction.setStatus("UNSUCCESSFULL");
		transactionRepo.save(transaction);
		return "TRANSACTION UNSUCCESSFULL - INSUFFICIENT BALANCE ";
	}

	@Override
	public String updateTransaction(Transaction transaction) {

		Customer customer = customerRepo.findById(transaction.getCustomer().getId()).get();
		transaction.setCustomer(customer);

		// set card number from credit card details of customer
		CreditCard creditcard = creditCardRepo.findById(customer.getCreditCard().getId()).get();
		transaction.setCardNumber(creditcard.getCardNumber());
		
		Transaction transaction1 = transactionRepo.findById(transaction.getTransid()).get();
		
		//will not be able to change previous date
		LocalDate tranDate1 = transaction1.getTranDate();
		transaction.setTranDate(tranDate1);
		double lastAmount = transaction1.getAmount();


		// update balance in account table after transaction amount changed
		Account account = accountRepo.findById(customer.getAccount().getAccountid()).get();
		if (account.getBalance() > transaction.getAmount()) {
			double updateBalance = account.getBalance() - transaction.getAmount() + lastAmount;
			accountRepo.setBalanceForAccount(updateBalance, account.getAccountid());
			transaction.setStatus("SUCCESSFULL");
			transactionRepo.save(transaction);

			// updating due amount in statement table for customer and billing month when
			// transaction amount is updated.
			double dueAmount=0;
			List<Statement> st = statementRepo.findAll();
			Statement s1 = null;
			for (Statement s : st) {
				if (tranDate1.isAfter(s.getBillingDate().plusDays(-31)) && tranDate1.isBefore(s.getBillingDate())) {
					// calculating amount for transactions done for last 31 days from billing date
					// -->billed statement
					System.out.println(s);
					s1 = s;
					dueAmount = transactionRepo.findAllTransactionsTranDate(s.getBillingDate().plusDays(-31),
							s.getBillingDate(), customer.getId(), "SUCCESSFULL");
					statementRepo.setAmountForStatementWhenTransactionUpdated(dueAmount,s.getBillingDate(),transaction1.getCustomer().getId());
				}
			}

			// Generating pdf for transaction details
			String path = pdfGenerator.transactionForCustomer(transaction.getTransid(), updateBalance);
			List<Transaction> tr = transactionRepo.findAllTransactionTranDate(s1.getBillingDate().plusDays(-31),s1.getBillingDate(), customer.getId(),
					"SUCCESSFULL");
			String path1 = pdfGenerator.showAllTransactionsAndStatementForCustomerIdAndBillingDatePdf(tr, customer.getId(),
					s1.getBillingDate().plusDays(-31), s1.getStatementid(),dueAmount);

			// sending mail to customer for successfull transaction in pdf with all
			// transaction details
			try {
				emailSenderService.sendEmailWithAttachment(customer.getEmail(),
						"Updated Transaction details attached with mail",
						"TRANSACTION SUCCESSFULL " + customer.getName(), path);
				emailSenderService.sendEmailWithAttachment(customer.getEmail(), "Statement details attached with mail",
						"STATEMENT GENERATED FOR " + s1.getBillingDate().getMonth() + " FOR " + customer.getName(),
						path1);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "TRANSACTION DETAILS UPDATED ";
		}

		return "TRANSACTION AMOUNT DETAILS CANNOT BE UPDATED  - INSUFFICIENT BALANCE ";
	}

	@Override
	public String deleteTransaction(long id) {
		transactionRepo.deleteById(id);
		return "TRANSACTION DELETED SUCCESSFULLY";
	}

	@Override
	public String showAllTranscationsForCustomerId(long custId) {
		Customer customer = customerRepo.findById(custId).get();
		List<Transaction> listTransactions = transactionRepo.findAllByCustomerId(custId);
		// Generating pdf for transaction details
		String path = pdfGenerator.showAllTransactionsForCustomerIdPdf(listTransactions, custId);

		// sending mail to customer for successfull transaction in pdf with all
		// transaction details
		try {
			emailSenderService.sendEmailWithAttachment(customer.getEmail(),
					"All Transaction details attached with mail", "TRANSACTION DETAILS FOR " + customer.getName(),
					path);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ALL TRANSACTION DETAILS SENT TO MAIL ";
	}

}
