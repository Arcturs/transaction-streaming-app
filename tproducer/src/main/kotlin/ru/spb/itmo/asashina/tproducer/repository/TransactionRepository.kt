package ru.spb.itmo.asashina.tproducer.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.spb.itmo.asashina.tproducer.model.entity.Transaction
import java.util.UUID

@Repository
interface TransactionRepository : CrudRepository<Transaction, UUID> {

    @Query(
        """
        select * 
        from transactions
        order by timestamp asc
        limit :batchSize
        for update skip locked
        """
    )
    fun getTransactionsInBatch(batchSize: Int): List<Transaction>

    fun deleteByIdIn(ids: List<UUID>)

}