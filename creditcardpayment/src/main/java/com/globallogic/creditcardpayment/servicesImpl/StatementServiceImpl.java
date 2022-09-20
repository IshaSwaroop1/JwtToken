package com.globallogic.creditcardpayment.servicesImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.creditcardpayment.entity.Account;
import com.globallogic.creditcardpayment.entity.Customer;
import com.globallogic.creditcardpayment.entity.Statement;
import com.globallogic.creditcardpayment.entity.Transaction;
import com.globallogic.creditcardpayment.pdfAndEmailService.EmailSenderService;
import com.globallogic.creditcardpayment.pdfAndEmailService.PdfGenerator;
import com.globallogic.creditcardpayment.repositories.AccountRepo;
import com.globallogic.creditcardpayment.repositories.CustomerRepo;
import com.globallogic.creditcardpayment.repositories.StatementRepo;
import com.globallogic.creditcardpayment.repositories.TransactionRepo;
import com.globallogic.creditcardpayment.services.StatementService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	StatementRepo statementRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	TransactionRepo transactionRepo;

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	PdfGenerator pdfGenerator;

	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public List<Statement> showAllStatement() {
		return statementRepo.findAll();
	}

	@Override
	public String addStatement(Statement statement) {
		Customer customer = customerRepo.findById(statement.getCustomer().getId()).get();
		statement.setCustomer(customer);

		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.withDayOfMonth(10);
		System.out.println(endDate);
		statement.setBillingDate(endDate);
		LocalDate startDate = endDate.plusDays(-31); // date of last month
		System.out.println(startDate);
		System.out.println(customer.getId());
		LocalDate lastDayOfMonthDate = currentDate
				.withDayOfMonth(currentDate.getMonth().length(currentDate.isLeapYear()));

		if (currentDate.isAfter(endDate) && currentDate.isBefore(lastDayOfMonthDate)) {

			// CHECKING IF STATEMENT IS GENERATED FOR CURRENT MONTH AND CUSTOMER ID
			if (statementRepo.checkForStatement(customer.getId(), endDate.getMonthValue(), endDate.getYear()) != null) {
				return "STATEMENT ALREADY GENERATED FOR THIS MONTH FOR CUSTOMER ID : " + customer.getId();
			}

			// calculating amount for transactions done for last 31 days from billing date
			// -->billed statement
			double dueAmount = transactionRepo.findAllTransactionsTranDate(startDate, endDate, customer.getId(),
					"SUCCESSFULL");
			statement.setDueAmount(dueAmount);
			statement.setDueDate(endDate.plusDays(10));
			statementRepo.save(statement);
			List<Transaction> tr = transactionRepo.findAllTransactionTranDate(startDate, endDate, customer.getId(),
					"SUCCESSFULL");
			String path = pdfGenerator.showAllTransactionsAndStatementForCustomerIdAndBillingDatePdf(tr,
					customer.getId(), startDate, statement.getStatementid(), dueAmount);
			// sending mail to customer for successfull transaction in pdf with all
			// transaction details
			try {
				emailSenderService.sendEmailWithAttachment(customer.getEmail(), "Statement details attached with mail",
						"STATEMENT GENERATED FOR " + statement.getBillingDate().getMonth() + " FOR "
								+ customer.getName(),
						path);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "STATEMENT CANNOT BE GENERATED BEFORE 10TH OF THE MONTH";
		}
		return "STATEMENT GENERATED FOR THIS MONTH AND SENT TO YOUR MAIL";
	}

	@Override
	public String updateStatement(Statement statement) {
		Customer customer = customerRepo.findById(statement.getCustomer().getId()).get();
		statement.setCustomer(customer);

		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.withDayOfMonth(10);
		System.out.println("eND DATE " + endDate);
		statement.setBillingDate(endDate);
		LocalDate startDate = endDate.plusDays(-31); // date of last month
		System.out.println("START DATE " + startDate);
		System.out.println(customer.getId());

		// calculating amount for transactions done for last 30 days from billing date
		// -->billed statement
		double dueAmount = transactionRepo.findAllTransactionsTranDate(startDate, endDate, customer.getId(),
				"SUCCESSFULL");
		statement.setDueAmount(dueAmount);
		statement.setDueDate(endDate.plusDays(10));
		statementRepo.save(statement);
		List<Transaction> tr = transactionRepo.findAllTransactionTranDate(startDate, endDate, customer.getId(),
				"SUCCESSFULL");
		String path = pdfGenerator.showAllTransactionsAndStatementForCustomerIdAndBillingDatePdf(tr, customer.getId(),
				startDate, statement.getStatementid(), dueAmount);
		// sending mail to customer for successfull transaction in pdf with all
		// transaction details
		try {
			emailSenderService.sendEmailWithAttachment(customer.getEmail(),
					"Updated Statement details attached with mail", "UPDATED STATEMENT GENERATED FOR "
							+ statement.getBillingDate().getMonth() + " FOR " + customer.getName(),
					path);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "STATEMENT UPDATED FOR THIS MONTH AND SENT TO YOUR MAIL";
	}

	@Override
	public String deleteStatement(long id) {
		statementRepo.deleteById(id);
		return "STATEMENT DELETED SUCCESSFULLY";
	}

	@Override
	public Statement showStatement(long id) {
		return statementRepo.findById(id).get();
	}

	@Override
	public String showBilledStatementForCustomer(long custId) {
		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.withDayOfMonth(10);
		LocalDate startDate = endDate.plusDays(-30);

		Customer customer = customerRepo.findById(custId).get();
		// CHECKING IF STATEMENT IS NOT GENERATED FOR PREVIOUS MONTH AND CUSTOMER ID
		if (statementRepo.checkForStatement(customer.getId(), endDate.getMonthValue(), endDate.getYear()) == null) {
			return "STATEMENT NOT GENERATED FOR PREVIOUS MONTH FOR CUSTOMER ID PLEASE GENERATE IN POST METHOD: "
					+ customer.getId();
		}

		List<Statement> st = statementRepo.findAll();
		for (Statement s : st) {
			if (s.getBillingDate().equals(endDate) && s.getCustomer().getId() == custId) {
				List<Transaction> tr = transactionRepo.findAllTransactionTranDate(startDate, endDate, custId,
						"SUCCESSFULL");
				String path = pdfGenerator.showBilledStatementWithAllTransactionsForCustomerIdAndBillingDatePdf(tr,
						custId, startDate, s.getStatementid());
				// sending mail to customer for successfull transaction in pdf with all
				// transaction details
				try {
					emailSenderService.sendEmailWithAttachment(customer.getEmail(),
							"Billed Statement details attached with mail", "BILLED STATEMENT GENERATED FOR "
									+ s.getBillingDate().getMonth() + " FOR " + customer.getName(),
							path);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "BILLED STATEMENT FOR PREVIOUS MONTH GENERATED";
	};

	@Override
	public String showUnbilledStatementForCustomer(long custId) {

		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = currentDate.withDayOfMonth(10);
		LocalDate endDate = startDate.plusDays(30);
		double sum = 0;
		Customer customer = customerRepo.findById(custId).get();

		if (currentDate.isAfter(startDate)) {
			List<Transaction> tr = transactionRepo.findAllTransactionTranDate(startDate, currentDate, custId,
					"SUCCESSFULL");
			for (Transaction t : tr) {
				sum = sum + t.getAmount();
			}
			String path = pdfGenerator.showUnBilledStatementWithAllTransactionsForCustomerIdAndBillingDatePdf(tr,
					custId, currentDate, sum);
			// sending mail to customer for successfull transaction in pdf with all
			// transaction details
			try {
				emailSenderService.sendEmailWithAttachment(customer.getEmail(),
						"UnBilled Statement details attached with mail", "UNBILLED STATEMENT GENERATED FOR "
								+ currentDate.plusDays(30).getMonth() + " FOR " + customer.getName(),
						path);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "UNBILLED STATEMENT GENERATED FOR CURRRENT MONTH";
		}

		else if (currentDate.isBefore(startDate)) {
			List<Transaction> tr = transactionRepo.findAllTransactionTranDate(startDate.plusDays(-30), currentDate, custId,
					"SUCCESSFULL");
			for (Transaction t : tr) {
				sum = sum + t.getAmount();
			}
			String path = pdfGenerator.showUnBilledStatementWithAllTransactionsForCustomerIdAndBillingDatePdf1(tr,
					custId, currentDate, sum);
			// sending mail to customer for successfull transaction in pdf with all
			// transaction details
			try {
				emailSenderService.sendEmailWithAttachment(customer.getEmail(),
						"UnBilled Statement details attached with mail", "UNBILLED STATEMENT GENERATED FOR "
								+ currentDate.getMonth() + " FOR " + customer.getName(),
						path);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "UNBILLED STATEMENT GENERATED FOR PREVIOUS MONTH";
		}

		return "UNBILLED STATEMENT FOR CURRENT MONTH GENERATED";
	}

}
