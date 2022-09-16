package org.aibles.library.document.configuration;

;
import org.aibles.library.document.converter.MappingHelper;
import org.aibles.library.document.dto.BasicDocumentResDTO;
import org.aibles.library.document.model.Document;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    public interface ModelMapperFactory {
        ModelMapper getMapper();
    }

    private ModelMapperFactory modelMapperFactory() {
        return () -> {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            mapper.typeMap(Document.class, BasicDocumentResDTO.class)
                    .addMappings(mapping -> {
                        mapping.map(doc -> doc.getCategories(), BasicDocumentResDTO::setCategories);
                    });

            return mapper;
        };
    }

    @Bean
    public MappingHelper mappingHelper() {
        return new MappingHelper(modelMapperFactory());
    }
}

