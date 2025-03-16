package com.evry.FinLimit.mappers;

import com.evry.FinLimit.entity.Limit;
import com.evry.FinLimit.model.LimitRequestDTO;
import com.evry.FinLimit.model.LimitResponseDTO;
import org.mapstruct.Mapper;

/**

 */
@Mapper(componentModel = "spring")
public interface LimitMapper {

    LimitResponseDTO toDTO(Limit limit);

    Limit toEntity(LimitRequestDTO limitDTO);
}
