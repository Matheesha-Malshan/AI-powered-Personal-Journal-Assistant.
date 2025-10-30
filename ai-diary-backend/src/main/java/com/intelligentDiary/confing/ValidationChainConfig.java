package com.intelligentDiary.confing;

import com.intelligentDiary.validator.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ValidationChainConfig {

    @Bean
    @Primary
    public ValidationChain makeChain(ContentValidator contentValidator,
                                     ProfanityFilter profanityFilter,
                                     MLReadinessValidator mlReadinessValidator){


        contentValidator.nextValidator=profanityFilter;
        profanityFilter.nextValidator=mlReadinessValidator;

        return contentValidator;
    }
}
