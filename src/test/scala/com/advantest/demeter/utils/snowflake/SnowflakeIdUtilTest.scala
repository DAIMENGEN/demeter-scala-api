package com.advantest.demeter.utils.snowflake

import com.advantest.demeter.utils.SnowflakeIdUtil
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers._

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
class SnowflakeIdUtilTest extends AnyFunSuiteLike {

  test("nextId should generate unique IDs") {
    // Generate multiple IDs and check if they are unique
    val ids = (1 to 100).map(_ => SnowflakeIdUtil.nextId())
    val uniqueIds = ids.distinct
    // Check if all generated IDs are unique
    ids.size shouldEqual uniqueIds.size
  }

  test("nextId should generate IDs within a valid range") {
    // Generate an ID and check if it falls within a valid range
    val id = SnowflakeIdUtil.nextId()
    // Assuming the valid range is from 0 to Long.MaxValue
    id should be >= 0L
    id should be <= Long.MaxValue
  }
}
