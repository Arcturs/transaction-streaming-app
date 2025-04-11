package ru.spb.itmo.asashina.tmonitoring.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.spb.itmo.asashina.tmonitoring.dictionary.FraudDetectionResultType
import ru.spb.itmo.asashina.tmonitoring.model.entity.FraudDetectionEntity
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface FraudDetectionRepository : CrudRepository<FraudDetectionEntity, UUID> {

    @Modifying
    @Query("""
        insert into fraud_detection(id, timestamp, result)
        values (:id, :timestamp, :result)
        on conflict do nothing
        """)
    fun saveWithoutConflict(id: UUID, timestamp: LocalDateTime, result: FraudDetectionResultType)

    @Query("""
        select *
        from fraud_detection fd
        where fd.showed = FALSE
        order by fd.timestamp
        limit :limit
        """)
    fun findActualWithLimit(limit: Int): List<FraudDetectionEntity>

}