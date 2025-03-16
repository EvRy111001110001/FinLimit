memberSearchIndex = [{"p":"com.evry.FinLimit.config","c":"AppConfig","l":"AppConfig()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"calculateRemainingLimits(List<TransactionWithLimitDTO>, BigDecimal, List<Long>, List<BigDecimal>)","u":"calculateRemainingLimits(java.util.List,java.math.BigDecimal,java.util.List,java.util.List)"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"CassandraConfig()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"cassandraTemplate(CqlSession)","u":"cassandraTemplate(com.datastax.oss.driver.api.core.CqlSession)"},{"p":"com.evry.FinLimit.controllers","c":"ClientController","l":"ClientController()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"convertCurrency(BigDecimal, CurrencyShortname, LocalDateTime)","u":"convertCurrency(java.math.BigDecimal,com.evry.FinLimit.entity.CurrencyShortname,java.time.LocalDateTime)"},{"p":"com.evry.FinLimit.services","c":"LimitService","l":"createNewLimit(Long, BigDecimal)","u":"createNewLimit(java.lang.Long,java.math.BigDecimal)"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"debtRepayment(BigDecimal, List<BigDecimal>)","u":"debtRepayment(java.math.BigDecimal,java.util.List)"},{"p":"com.evry.FinLimit.cassandra","c":"ExchangeRate","l":"ExchangeRate()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.cassandra","c":"ExchangeRateKey","l":"ExchangeRateKey()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.model","c":"ExchangeRateResponse","l":"ExchangeRateResponse()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.services","c":"ExchangeRateService","l":"ExchangeRateService()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.repositories","c":"LimitRepository","l":"existsByAccountFrom(Long)","u":"existsByAccountFrom(java.lang.Long)"},{"p":"com.evry.FinLimit.component","c":"ExternalExchangeRateClient","l":"ExternalExchangeRateClient(RestTemplate)","u":"%3Cinit%3E(org.springframework.web.client.RestTemplate)"},{"p":"com.evry.FinLimit.component","c":"ExternalExchangeRateClient","l":"fetchExchangeRate(String)","u":"fetchExchangeRate(java.lang.String)"},{"p":"com.evry.FinLimit.repositories","c":"LimitRepository","l":"findByAccountIdOrderByDateAsc(Long)","u":"findByAccountIdOrderByDateAsc(java.lang.Long)"},{"p":"com.evry.FinLimit.cassandra","c":"ExchangeRateRepository","l":"findByCurrencyPairAndDate(String, Instant)","u":"findByCurrencyPairAndDate(java.lang.String,java.time.Instant)"},{"p":"com.evry.FinLimit.cassandra","c":"ExchangeRateRepository","l":"findLatestByCurrencyPair(String)","u":"findLatestByCurrencyPair(java.lang.String)"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"findRelevantLimit(TransactionWithLimitDTO, List<Limit>)","u":"findRelevantLimit(com.evry.FinLimit.model.TransactionWithLimitDTO,java.util.List)"},{"p":"com.evry.FinLimit.repositories","c":"TransactionRepository","l":"findTransactionsWithLimit(Long)","u":"findTransactionsWithLimit(java.lang.Long)"},{"p":"com.evry.FinLimit","c":"FinLimitApplication","l":"FinLimitApplication()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.controllers","c":"ClientController","l":"getAllLimit(Long)","u":"getAllLimit(java.lang.Long)"},{"p":"com.evry.FinLimit.services","c":"LimitService","l":"getAllLimit(Long)","u":"getAllLimit(java.lang.Long)"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"getEntityBasePackages()"},{"p":"com.evry.FinLimit.services","c":"ExchangeRateService","l":"getExchangeRate(String, Instant)","u":"getExchangeRate(java.lang.String,java.time.Instant)"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"getKeyspaceCreations()"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"getKeyspaceDrops()"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"getKeyspaceName()"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"getSchemaAction()"},{"p":"com.evry.FinLimit.controllers","c":"ClientController","l":"getTransactionsExceedingLimits(Long)","u":"getTransactionsExceedingLimits(java.lang.Long)"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"getTransactionsExceedingLimits(Long)","u":"getTransactionsExceedingLimits(java.lang.Long)"},{"p":"com.evry.FinLimit.cassandra","c":"CassandraConfig","l":"KEYSPACE"},{"p":"com.evry.FinLimit.entity","c":"CurrencyShortname","l":"KZT"},{"p":"com.evry.FinLimit.entity","c":"Limit","l":"Limit()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.model","c":"LimitRequestDTO","l":"LimitRequestDTO()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.model","c":"LimitResponseDTO","l":"LimitResponseDTO()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.services","c":"LimitService","l":"LimitService()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit","c":"FinLimitApplication","l":"main(String[])","u":"main(java.lang.String[])"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"processTransactionsByLimit(Map<Limit, List<TransactionWithLimitDTO>>, List<Long>, List<BigDecimal>)","u":"processTransactionsByLimit(java.util.Map,java.util.List,java.util.List)"},{"p":"com.evry.FinLimit.config","c":"AppConfig","l":"restTemplate()"},{"p":"com.evry.FinLimit.entity","c":"CurrencyShortname","l":"RUB"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"save(TransactionDTO)","u":"save(com.evry.FinLimit.model.TransactionDTO)"},{"p":"com.evry.FinLimit.controllers","c":"TransactionController","l":"saveTransaction(TransactionDTO)","u":"saveTransaction(com.evry.FinLimit.model.TransactionDTO)"},{"p":"com.evry.FinLimit.controllers","c":"ClientController","l":"settingNewLimit(Long, LimitRequestDTO)","u":"settingNewLimit(java.lang.Long,com.evry.FinLimit.model.LimitRequestDTO)"},{"p":"com.evry.FinLimit.mappers","c":"LimitMapper","l":"toDTO(Limit)","u":"toDTO(com.evry.FinLimit.entity.Limit)"},{"p":"com.evry.FinLimit.mappers","c":"TransactionMapper","l":"toDTO(Transaction)","u":"toDTO(com.evry.FinLimit.entity.Transaction)"},{"p":"com.evry.FinLimit.mappers","c":"LimitMapper","l":"toEntity(LimitRequestDTO)","u":"toEntity(com.evry.FinLimit.model.LimitRequestDTO)"},{"p":"com.evry.FinLimit.mappers","c":"TransactionMapper","l":"toEntity(TransactionDTO)","u":"toEntity(com.evry.FinLimit.model.TransactionDTO)"},{"p":"com.evry.FinLimit.entity","c":"Transaction","l":"Transaction()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.controllers","c":"TransactionController","l":"TransactionController()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.model","c":"TransactionDTO","l":"TransactionDTO()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit","c":"TransactionParallelTest","l":"TransactionParallelTest()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.services","c":"TransactionService","l":"TransactionService()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit","c":"TransactionServiceTest","l":"TransactionServiceTest()","u":"%3Cinit%3E()"},{"p":"com.evry.FinLimit.model","c":"TransactionWithLimitDTO","l":"TransactionWithLimitDTO(Long, Long, Long, String, BigDecimal, String, Timestamp, Timestamp, BigDecimal, String)","u":"%3Cinit%3E(java.lang.Long,java.lang.Long,java.lang.Long,java.lang.String,java.math.BigDecimal,java.lang.String,java.sql.Timestamp,java.sql.Timestamp,java.math.BigDecimal,java.lang.String)"},{"p":"com.evry.FinLimit.entity","c":"CurrencyShortname","l":"USD"},{"p":"com.evry.FinLimit.entity","c":"CurrencyShortname","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"com.evry.FinLimit.entity","c":"CurrencyShortname","l":"values()"}];updateSearchResults();