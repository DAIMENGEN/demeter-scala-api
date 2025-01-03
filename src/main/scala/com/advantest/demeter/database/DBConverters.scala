package com.advantest.demeter.database

import java.time.LocalDateTime
import scala.language.implicitConversions

/**
 * Create on 2025/01/03
 * Author: mengen.dai@outlook.com
 */
trait DBConverters {

  implicit def toDBLongValue(value: Long): DBLongValue = DBLongValue(value)

  implicit def toDBLongValue(values: Seq[Long]): Seq[DBLongValue] = values.map(DBLongValue(_))

  implicit def toDBVarcharValue(value: String): DBVarcharValue = DBVarcharValue(value)

  implicit def toDBDateTimeValue(value: LocalDateTime): DBDateTimeValue = DBDateTimeValue(value)
}
