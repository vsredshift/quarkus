package org.vsredshift.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
// import org.slf4j.Logger; // required when not using annotation

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Slf4j
public class CustomerResource {

    private final CustomerService customerService;
//    private final Logger logger;      // if not using annotation -> injection from LoggerProducer --> use instead e.g. log.info("")

    // The @APIResponses definitions are a way to inline the Swagger documentation directly in the code.
    // The annotations generate some noise,
    // but reduce the need to maintain the implementation class separately from the Swagger definition.

    @GET
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Get All Customers",
                    content = @Content (mediaType = "application/json",
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Customer.class))
            )
    })
    public Response get() {
        return Response.ok(customerService.findAll()).build();
    }

    @GET
    @Path("/{customerId}")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Get Customer by customerId",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Customer.class))),
            @APIResponse(
                    responseCode = "404",
                    description = "No Customer found for customerId provided",
                    content = @Content(mediaType = "application/json")
            ),
    })
    public Response getById(@PathParam("customerId") Integer customerId) {
        Optional<Customer> optional = customerService.findById(customerId);
        return optional.isPresent() ? Response.ok(optional.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Customer created",
                            content = @Content (mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = Customer.class))
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Customer already exists for customerId",
                            content = @Content(mediaType = "application/json")
                    ),
            }
    )
    public Response post(@Valid Customer customer) {
        final Customer saved = customerService.save(customer);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Customer updated",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = Customer.class))
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "No Customer found for customerId provided",
                            content = @Content(mediaType = "application/json")
                    ),

            }
    )
    public Response put(@Valid Customer customer) {
        final Customer saved = customerService.update(customer);
        return Response.ok(saved).build();
    }
}