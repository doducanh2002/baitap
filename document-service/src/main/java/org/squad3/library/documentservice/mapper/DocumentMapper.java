package org.squad3.library.documentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.squad3.library.documentservice.dto.DocumentDTO;
import org.squad3.library.documentservice.model.Document;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDTO convertToDto(Document document);

    Document convertToEntity(DocumentDTO documentDTO);

}
