package com.mrmessy.messenger.controllers;

import com.mrmessy.messenger.entities.User;
import com.mrmessy.messenger.graphql.schema.GraphQLContext;
import com.mrmessy.messenger.services.UserService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class GraphQLController {

    private static final String HEADER_LOGIN = "x-auth0-email";

    private final GraphQL graphql;
    private final UserService userService;

    public GraphQLController(GraphQL graphql, UserService userService) {
        this.graphql = graphql;
        this.userService = userService;
    }

    @RequestMapping(value = "/graphql", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public Object executeOperation(@RequestBody Map body,
                                   @RequestHeader Map header) {
        Object context = getContext(header);
        String query = (String) body.get("query");
        Map<String, Object> result = new LinkedHashMap<>();

        @SuppressWarnings("unchecked")
        Map<String, Object> variables = body.get("variables") == null ?
                Collections.emptyMap() : (Map<String, Object>) body.get("variables");

        ExecutionInput input = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .context(context)
                .build();

        ExecutionResult executionResult = graphql.execute(input);
        if (!executionResult.getErrors().isEmpty()) {
            result.put("errors", executionResult.getErrors());
            log.error("Errors: {}", executionResult.getErrors());
        }
        result.put("data", executionResult.getData());
        return result;
    }

    private Object getContext(Map headers) {
        String email = (String) headers.get(HEADER_LOGIN);
        User user = userService.getByEmail(email).orElse(null);
        GraphQLContext context = new GraphQLContext(user);
        return context;
    }

}
