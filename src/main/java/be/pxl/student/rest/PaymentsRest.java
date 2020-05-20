package be.pxl.student.rest;

import be.pxl.student.dao.EntityManagerUtil;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("payments")
public class PaymentsRest {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{paymentId}/link/{labelId}")
    public Response addLabelToPayment(@PathParam("paymentId") int paymentId, @PathParam("labelId") int labelId) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelDao labelDao = new LabelDao(entityManager);
        PaymentDao paymentDao = new PaymentDao(entityManager);

        Payment payment = paymentDao.getById(paymentId);
        try {
            if(payment == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("invalid paymentId").build();
            }
            if(payment.getLabel() != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("payment already has label").build();
            }

            Label label = labelDao.getById(labelId);

            if(label == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("invalid labelId").build();
            }

            payment.setLabel(label);

            paymentDao.updatePayment(payment);

            return Response.status(Response.Status.OK).build();
        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @DELETE
    @Path("{paymentId}")
    public Response deletePayment(@PathParam("paymentId") int id) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        PaymentDao paymentDao = new PaymentDao(entityManager);

        try {
            Payment payment = paymentDao.getById(id);
            if(payment == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("payment bestaat niet").build();
            }
            paymentDao.deletePayment(payment);

            return Response.status(Response.Status.ACCEPTED).build();

        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }
}
