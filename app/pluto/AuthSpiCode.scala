package pluto

import com.iteration3.smile.kplay.action.AuthSpi
import play.api.mvc.RequestHeader
import com.iteration3.smile._
import play.api.i18n.Messages

// sample implementation
class AuthSpiCode  extends AuthSpi {


  def checkAuthentication(requestHeader: RequestHeader): Boolean =
    new UserInfo(requestHeader).isUserInSession


  def checkAuthorization(appId:String, requestHeader: RequestHeader, fqActionName: String, allowed: Boolean, givenRoleList: List[String]): Boolean = {
    true
    /*
    val user = new UserInfo(requestHeader)
    val userRoleList = Smile.grab[com.netastay.gompaapidomain.domain.AppApiDomainLogic].getAppRoles(appId, user.userName.get)

    if (allowed) {
      givenRoleList.exists(x => userRoleList.contains(x))
    } else {
      !givenRoleList.exists(x => userRoleList.contains(x))
    }
     */
  }

  override def getAuthenticatedUser(requestHeader: RequestHeader):Option[String] = {
    new UserInfo(requestHeader).userName
  }

  override def getUserSessionKey:String = "userName"

  class UserInfo(rh: RequestHeader) {
    val userName: Option[String] = rh.session.get(getUserSessionKey)

    def isUserInSession: Boolean = userName.isDefined

  }

}




