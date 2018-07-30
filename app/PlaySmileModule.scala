import com.iteration3.Smile
import com.iteration3.smile._
import com.iteration3.Smile._
import play.api.Configuration
import play.api.Environment
import play.api.inject.Binding
import play.api.inject.Module

class PlaySmileModule extends Module {
  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[SmileStarter].to[SmileStarterImpl].eagerly()
    )
  }
}

import javax.inject.Inject
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future

trait SmileStarter

class SmileStarterImpl @Inject()(lifecycle: ApplicationLifecycle) extends SmileStarter {
  Smile.start(serverMode = true, classLoader = this.getClass.getClassLoader)
  smileLogger.info("Smile  started.(" + currentEnv.envName + ")");
  lifecycle.addStopHook { () =>
    smileLogger.info("Smile shutdown...")
    Smile.stop()
    smileLogger.info("Smile shutdown completed.")
    Future.successful(())
  }
}
