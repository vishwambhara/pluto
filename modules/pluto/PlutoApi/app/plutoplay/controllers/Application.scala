package plutoplay.controllers

import play.api._
import play.api.mvc._


object Application extends Controller {

  def index = Action {
    Ok("Play module PlutoApi is ready.")
  }

}
