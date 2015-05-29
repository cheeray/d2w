package com.d2w.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.d2w.core.TypedData;
import com.d2w.exe.TowException;
import com.d2w.service.DataService;
import com.d2w.utils.JsonUtil;

/**
 * A simple REST service which is able to fetch data from a path.
 * 
 * @author Chengwei.Yan
 * 
 */
@Path("/")
public class Guide {
	@Inject
	DataService data;

	@GET
	@Path("/")
	//@Produces({ "application/json" })
	public String scan() {
		return JsonUtil.toJson(data.paths());
	}

	@GET
	@Path("/{class}/{field}/{id}")
	public Response find(@PathParam("class") String clazz,
			@PathParam("field") String field, @PathParam("id") String id)
			throws TowException {
		if (clazz == null) {
			return Response.ok(JsonUtil.toJson(data.paths()),
					MediaType.APPLICATION_JSON_TYPE).build();
		}
		if (field == null) {
			return Response.ok(JsonUtil.toJson(data.paths(clazz)),
					MediaType.APPLICATION_JSON_TYPE).build();
		}
		final TypedData exist = data.find(clazz, field, id);
		return Response.ok(exist.getData(), exist.getType()).build();
	}
}
