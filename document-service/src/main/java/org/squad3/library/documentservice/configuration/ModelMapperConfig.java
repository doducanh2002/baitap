package org.squad3.library.documentservice.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.squad3.library.documentservice.converter.MappingHelper;
import org.squad3.library.documentservice.dto.DocumentDTO;
import org.squad3.library.documentservice.dto.DocumentRes;
import org.squad3.library.documentservice.model.Document;

@Configuration
public class ModelMapperConfig {

    public interface ModelMapperFactory {
        ModelMapper getMapper();
    }

    private ModelMapperFactory modelMapperFactory() {
        return () -> {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            mapper.typeMap(Document.class, DocumentRes.class)
                    .addMappings(mapping -> {
                        mapping.map(doc -> doc.getCategories(), DocumentRes::setCategories);
                    });
            mapper.typeMap(Document.class, DocumentDTO.class)
                    .addMappings(mapping -> {
                        mapping.map(doc -> doc.getCategories(), DocumentDTO::setCategories);
                    });

            return mapper;
        };
    }

    @Bean
    public MappingHelper mappingHelper() {
        return new MappingHelper(modelMapperFactory());
    }
}
