package com.advantest.demeter.utils.snowflake

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 *
 * SnowflakeIdUtil: A utility class for generating unique identifiers using the Snowflake algorithm.
 */
private class SnowflakeIdUtil(workerId: Long = 1, datacenterId: Long = 1) {
  require(workerId >= 0 && workerId < 32, "workerId must be between 0 and 31")
  require(datacenterId >= 0 && datacenterId < 32, "datacenterId must be between 0 and 31")

  private val twepoch = 1288834974657L
  private val sequenceBits = 12L
  private val workerIdBits = 5L
  private val datacenterIdBits = 5L
  private val sequenceMask = -1L ^ (-1L << sequenceBits)
  private val workerIdShift = sequenceBits
  private val datacenterIdShift = sequenceBits + workerIdBits
  private val timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits
  private var sequence = 0L
  private var lastTimestamp = -1L

  private def tilNextMillis(lastTimestamp: Long): Long = {
    var timestamp = System.currentTimeMillis()
    while (timestamp <= lastTimestamp) {
      timestamp = System.currentTimeMillis()
    }
    timestamp
  }

  def nextId(): Long = synchronized {
    var timestamp = System.currentTimeMillis()
    if (timestamp < lastTimestamp) {
      throw new RuntimeException("Clock is moving backwards. Rejecting requests until " + lastTimestamp)
    }
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp)
      }
    } else {
      sequence = 0
    }
    lastTimestamp = timestamp
    ((timestamp - twepoch) << timestampLeftShift) |
      (datacenterId << datacenterIdShift) |
      (workerId << workerIdShift) |
      sequence
  }
}

object SnowflakeIdUtil {

  private val snowflakeIdUtil = new SnowflakeIdUtil()

  def nextId(): Long = snowflakeIdUtil.nextId()
}
