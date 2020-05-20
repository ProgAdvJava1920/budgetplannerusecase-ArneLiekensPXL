package be.pxl.student.rest;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.EntityManagerUtil;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resource.AccountResource;
import be.pxl.student.rest.resource.GetPaymentsResource;
import be.pxl.student.rest.resource.PaymentResource;
import jdk.jshell.spi.ExecutionControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("accounts")
public class AccountsRest {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(AccountResource accountResource) {
        Logger logger = LogManager.getLogger();
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        logger.debug("test");
        AccountDao accountDao = new AccountDao(entityManager);
        logger.debug("I get configured correct");

        if(accountDao.getByName(accountResource.getName()) == null) {
            Account account = mapAccountResource(accountResource);
            logger.debug(account.getId());
            account = accountDao.addAccount(account);
            entityManager.close();

            return Response.status(Response.Status.CREATED).build();
        } else if(accountDao.getByIban(accountResource.getIban()) != null) {
            Account account = accountDao.getByIban(accountResource.getIban());
            if(account.getName() == null) {
                account.setName(accountResource.getName());
                accountDao.updateAccount(account);
                return Response.status(Response.Status.OK).build();
            }

            return Response.status(Response.Status.BAD_REQUEST).entity(String.format("There already exists an account with iban [%s]", accountResource.getIban())).build();

        }


        logger.debug("ik geraak hier");
        entityManager.close();
        return Response.status(Response.Status.BAD_REQUEST).entity(String.format("There already exists an account with name [%s]", accountResource.getName())).build();
    }

    @POST
    @Path("{accountName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPayment(PaymentResource paymentResource, @PathParam("accountName") String accountName) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        AccountDao accountDao = new AccountDao(entityManager);
        PaymentDao paymentDao = new PaymentDao(entityManager);

        Account account = accountDao.getByName(accountName);
        if(account == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Account does not exist").build();
        }

        Payment payment = mapPaymentResource(paymentResource);

        if(payment == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("invalid argument").build();
        }

        Account counter = accountDao.getByIban(paymentResource.getCounterAccount());

        if(counter == null) {
            counter = new Account();
            counter.setIBAN(paymentResource.getCounterAccount());
            counter = accountDao.addAccount(counter);
        }

        payment.setCounterAccount(counter);
        payment.setAccount(account);

        paymentDao.addPayment(payment);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{accountName}")
    public Response getPayments(@PathParam("accountName") String accountName, @QueryParam("label") String labelName) {
        Logger logger = LogManager.getLogger();
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        AccountDao accountDao = new AccountDao(entityManager);
        PaymentDao paymentDao = new PaymentDao(entityManager);

        Account account = accountDao.getByName(accountName);
        if(account == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Account does not exist").build();
        }

        GetPaymentsResource paymentsResource = new GetPaymentsResource();

        List<Payment> payments = null;
        if(labelName != null) {
            payments = new ArrayList<>();
            for (Payment payment : account.getPayments()) {
                if(payment.getLabel() != null) {
                    if(payment.getLabel().getName().toLowerCase().equals(labelName.toLowerCase())) {
                        payments.add(payment);
                    }
                }
            }
        } else {
            payments = account.getPayments();
        }
        
        paymentsResource.mapPayments(payments);
        
        float receiving = (float)payments.stream().filter(p -> p.getAmount() > 0).mapToDouble(Payment::getAmount).sum();
        float spending = (float)payments.stream().filter(p -> p.getAmount() < 0).mapToDouble(Payment::getAmount).sum();

        
        paymentsResource.setReceivingAmount(receiving);
        paymentsResource.setSpendingAmount(spending);
        paymentsResource.setResultAmount(receiving + spending);

        return Response.ok(paymentsResource).build();

    }

    private Account mapAccountResource(AccountResource accountResource) {
        Account account = new Account();

        account.setIBAN(accountResource.getIban());
        account.setName(accountResource.getName());

        return account;
    }

    private Payment mapPaymentResource(PaymentResource paymentResource) {

        try {
            Payment payment = new Payment();

            payment.setAmount(paymentResource.getAmount());
            payment.setDetail(paymentResource.getDetail());

            LocalDateTime localDateTime;
            if(paymentResource.getDate() == null) {
                localDateTime = LocalDateTime.now();
            } else {
                String[] datesplit = paymentResource.getDate().split("/");
                localDateTime = LocalDateTime.of(Integer.parseInt(datesplit[2]), Integer.parseInt(datesplit[1]), Integer.parseInt(datesplit[0]), 0,0);
            }

            payment.setDate(localDateTime);

            return payment;
        } catch (Exception e) {
            return null;
        }


    }
}
