package pluto

import com.iteration3.smile.utils.MapUtil
import play.api.mvc.RequestHeader

case class RequestLog(id: Long, actionName: String, inputString: String, timeTaken: Long, responseStatus: Int)

class AccessLogSpiCode extends com.iteration3.smile.kplay.action.AccessLogSpi {
  def log(id: Long, actionName: String, inputMap: Map[String, Any], timeTaken: Long, responseStatus: Int, requestHeader: RequestHeader): Unit = {
    val log = RequestLog(id, actionName, MapUtil.toJson(inputMap).toString, timeTaken, responseStatus);
    com.iteration3.smile.playLogger.info(log.toString)
  }
}
