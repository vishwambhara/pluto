package pluto.controllers

import play.api.mvc._



object Application  extends Controller {

  def index = Action {
    Ok("Application pluto is ready.")
  }

  def swaggerPlayModules = Action {

    val options = play.api.libs.json.Json.toJson(
      com.iteration3.smile.SmileModule.playModuleNodes.flatMap {
        psm => val visibleList = psm.conf.getStringList("swagger.visible")
          psm.metadata.swaggerNameList.filter(name => if(visibleList.isEmpty) true else visibleList.get.contains(name)).map  {
            sn =>  s"""{ "id" : "/${psm.metadata.routeFragment}/${sn.toLowerCase}", "value" : "$sn (${psm.name})"}"""
          }
      }.mkString("[", ",", "]")
    )
    Ok(options)
  }



}
