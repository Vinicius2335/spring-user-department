package com.projetinho.userDept.config;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.projetinho.userDept.handler.ExceptionDetails;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SpringDocConfig {
	private static final String badRequestResponse = "BadRequestResponse";
	private static final String internalServerErrorResponse = "InternalServerErrorResponse";
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Departament API")
                        .version("v1")
                        .description("REST API de Testes")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.com")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("Teste")
                        .url("https://google.com.br")
                ).tags(Arrays.asList(
                        new Tag().name("Departaments").description("Gerencia os departaments"),
                        new Tag().name("Users").description("Gerencia os users")
                )).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
    }
    
    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                switch (httpMethod) {
                                    case GET:
                                        responses.addApiResponse("500", new ApiResponse()
                                        		.$ref(internalServerErrorResponse));
                                        break;
                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse()
                                        		.$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse()
                                        		.$ref(internalServerErrorResponse));
                                        break;
                                    case PUT:
                                        responses.addApiResponse("400", new ApiResponse()
                                        		.$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse()
                                        		.$ref(internalServerErrorResponse));
                                        break;
                                    case DELETE:
                                        responses.addApiResponse("500", new ApiResponse()
                                        		.$ref(internalServerErrorResponse));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse()
                                        		.$ref(internalServerErrorResponse));
                                        break;
                                }
                            })
                    );
        };
    }
    
    @SuppressWarnings("rawtypes")
	private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(ExceptionDetails.class);

        schemaMap.putAll(problemSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<ExceptionDetails>().$ref("ExceptionDetails")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }
}
