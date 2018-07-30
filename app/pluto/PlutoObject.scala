package pluto

import com.iteration3.smile._
import com.iteration3.Smile
import com.iteration3.Smile._
import com.iteration3.smile.SmileModuleObject


object PlutoObject extends SmileModuleObject {

lazy val conf = Smile.conf

lazy val lifecycle = grab[PlutoLifeCycle]



}
