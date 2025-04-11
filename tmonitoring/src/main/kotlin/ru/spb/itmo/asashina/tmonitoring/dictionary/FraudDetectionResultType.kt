package ru.spb.itmo.asashina.tmonitoring.dictionary

enum class FraudDetectionResultType(
    private val code: String,
) {

    FRAUD("Fraud"),
    NOT_FRAUD("Not Fraud"),
    ERROR("ERROR");

    companion object {

        @JvmStatic
        fun fromCode(code: String): FraudDetectionResultType = entries.first { it.code == code }

    }

}