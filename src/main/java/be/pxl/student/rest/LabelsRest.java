package be.pxl.student.rest;

import be.pxl.student.dao.EntityManagerUtil;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resource.LabelResource;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("labels")
public class LabelsRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLabels() {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelDao labelDao = new LabelDao(entityManager);
        List<Label> labels = labelDao.getAll();

        if(entityManager != null) {
            entityManager.close();
        }
        return Response.ok(labels).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLabel(LabelResource labelResource) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelDao labelDao = new LabelDao(entityManager);

        try {
            if(labelDao.getByName(labelResource.getName()) != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(String.format("Er bestaat al een label met naam [%s]", labelResource.getName())).build();
            }

            labelDao.addLabel(mapLabelResource(labelResource));

            return Response.status(Response.Status.CREATED).build();
        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    @DELETE
    @Path("{labelId}")
    public Response deleteLabel(@PathParam("labelId") int labelId) {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        LabelDao labelDao = new LabelDao(entityManager);
        PaymentDao paymentDao = new PaymentDao(entityManager);

        try {
            Label label = labelDao.getById(labelId);
            List<Payment> payments = paymentDao.getAll();
            if(payments.stream().anyMatch(p -> p.getLabel().equals(label))) {
                return Response.status(Response.Status.BAD_REQUEST).entity(String.format("Label [%s] is in use. Remove payments first or change their label", label.getName())).build();
            }

            labelDao.deleteLabel(label);

            return Response.status(Response.Status.ACCEPTED).build();

        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    private Label mapLabelResource(LabelResource labelResource) {
        Label label = new Label();
        label.setName(labelResource.getName());

        return label;
    }
}
