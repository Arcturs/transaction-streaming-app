package ru.spb.itmo.asashina.tmonitoring.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.spb.itmo.asashina.tmonitoring.model.entity.CategoryStatEntity
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface CategoryStatRepository : CrudRepository<CategoryStatEntity, UUID> {

    @Modifying
    @Query("""
        insert into category_stats(timestamp, category, count, max_amount, min_amount, sum)
        values (:timestamp, :category, :count, :maxAmount, :minAmount, :sum)
        on conflict do nothing
        """)
    fun saveWithoutConflict(
        timestamp: LocalDateTime,
        category: String,
        count: BigInteger,
        maxAmount: BigDecimal,
        minAmount: BigDecimal,
        sum: BigDecimal)

    @Query("""
        select *
        from category_stats fd
        where fd.showed = FALSE
        order by fd.timestamp
        limit :limit
        """)
    fun findActualWithLimit(limit: Int): List<CategoryStatEntity>

}