package com.advantest.demeter.utils.database

import com.advantest.demeter.utils.json.JsonObject

import java.time.{LocalDate, LocalDateTime}

/**
 * Create on 2024/12/18
 * Author: mengen.dai@outlook.com
 */
sealed trait DBFieldValue

/**
 * DBIntValue, representing an int field in the database.
 * @param value The actual int value.
 */
case class DBIntValue(value: Int) extends DBFieldValue

/**
 * DBLongValue, representing a long field in the database.
 * @param value The actual long value.
 */
case class DBLongValue(value: Long) extends DBFieldValue

/**
 * DBTextValue, representing a text field in the database.
 * @param value The actual string value.
 */
case class DBTextValue(value: String) extends DBFieldValue

/**
 * DBFloatValue, representing a float field in the database.
 * @param value The actual float value.
 */
case class DBFloatValue(value: Float) extends DBFieldValue

/**
 * DBDoubleValue, representing a double field in the database.
 * @param value The actual double value.
 */
case class DBDoubleValue(value: Double) extends DBFieldValue

/**
 * DBStringValue, representing a string field in the database.
 * @param value The actual string value.
 */
case class DBStringValue(value: String) extends DBFieldValue

/**
 * DBBooleanValue, representing a boolean field in the database.
 * @param value The actual boolean value.
 */
case class DBBooleanValue(value: Boolean) extends DBFieldValue

/**
 * DBJsonValue, representing a json field in the database.
 * @param value The actual json value.
 */
case class DBJsonValue(value: JsonObject) extends DBFieldValue

/**
 * DBDateValue, representing a date field in the database.
 * @param value The actual date value.
 */
case class DBDateValue(value: LocalDate) extends DBFieldValue

/**
 * DateTimeValue, representing a datetime field in the database.
 * @param value The actual datetime value.
 */
case class DBDateTimeValue(value: LocalDateTime) extends DBFieldValue