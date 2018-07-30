package pluto

import com.google.inject.AbstractModule

class PlutoBinder extends AbstractModule {

def configure () {
bind (classOf[com.iteration3.smile.kplay.action.AuthSpi] ).to (classOf[AuthSpiCode] );
bind(classOf[com.iteration3.smile.kplay.action.AccessLogSpi]).to(classOf[AccessLogSpiCode]);
}
}

