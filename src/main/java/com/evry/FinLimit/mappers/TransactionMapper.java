package com.evry.FinLimit.mappers;

import com.evry.FinLimit.entity.Transaction;
import com.evry.FinLimit.model.TransactionDTO;
import org.mapstruct.Mapper;

/**
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toDTO(Transaction transaction);

    Transaction toEntity(TransactionDTO transactionDTO);
}
