package ru.spb.itmo.asashina.tproducer.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.spb.itmo.asashina.tproducer.model.entity.Transaction
import java.util.UUID

@Repository
interface TransactionRepository : CrudRepository<Transaction, UUID>