package com.advantest.demeter.service

import com.advantest.demeter.DemeterScalaApi.DEMETER_SYSTEM
import com.advantest.demeter.database.DBConverters
import org.slf4j.Logger

/**
 * Create on 2024/10/13
 * Author: mengen.dai@outlook.com
 */
trait Service extends DBConverters {
  protected val logger: Logger = DEMETER_SYSTEM.log
}
