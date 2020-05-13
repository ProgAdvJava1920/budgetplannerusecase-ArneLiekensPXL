package be.pxl.student.rest;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.EntityManagerUtil;
import be.pxl.student.entity.Account;
import be.pxl.student.rest.resource.AccountResource;
import jdk.jshell.spi.ExecutionControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
    public Response createPayment() {
        throw new NotYetImplementedException();
    }

    private Account mapAccountResource(AccountResource accountResource) {
        Account account = new Account();

        account.setIBAN(accountResource.getIban());
        account.setName(accountResource.getName());

        return account;
    }
}
