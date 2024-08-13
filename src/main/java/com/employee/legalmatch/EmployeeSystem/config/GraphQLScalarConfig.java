package com.employee.legalmatch.EmployeeSystem.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class GraphQLScalarConfig {
    private final DateTimeFormatter requestFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME; // With Timezone - 2011-12-03T10:15:30+01:00
    private final DateTimeFormatter responseFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME; // With Timezone - 2011-12-03T10:15:30+01:00
    private final GraphQLScalarType DateScalar = GraphQLScalarType.newScalar()
            .name("ZonedDateTime")
            .description("A custom scalar that handles Date")
            .coercing(new Coercing<>() {
                @Override
                public Object serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) throws CoercingSerializeException {
                    try {
                        ZonedDateTime publishedTime = (ZonedDateTime) dataFetcherResult;
                        return responseFormatter.format(publishedTime);
                    } catch (CoercingSerializeException exception) {
                        throw new CoercingSerializeException("Invalid Input: " + exception.getMessage());
                    }
                }

                /**
                 * Converts input string date to UTC ZonedDateTime
                 */
                @Override
                public Object parseValue(Object input, GraphQLContext graphQLContext, Locale locale) throws CoercingParseValueException {
                    try{
                        return ZonedDateTime.parse((String) input).withZoneSameInstant(ZoneId.of("UTC"));
                    } catch (RuntimeException exception) {
                        throw new CoercingParseValueException("Invalid Input: " + exception.getMessage());
                    }
                }

                @Override
                public Object parseLiteral(Value input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
                    try {
                        var stringInput = (StringValue) input;
                        return ZonedDateTime.parse(stringInput.getValue(), requestFormatter);
                    } catch (RuntimeException exception) {
                        throw new CoercingParseLiteralException("Invalid Input: " + exception.getMessage());
                    }
                }

                @Override
                public Value<?> valueToLiteral(Object input, GraphQLContext graphQLContext, Locale locale) {
                    return new StringValue(input.toString());
                }
            }).build();

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder.scalar(DateScalar);
    }
}
