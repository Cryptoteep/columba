package network.columba.app.rns.backend.kt

import network.columba.app.rns.api.model.InterfaceConfig
import network.reticulum.common.InterfaceMode
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Test

class NativeInterfaceFactoryModeTest {

    @After
    fun tearDown() {
        NativeInterfaceFactory.shutdownAll()
    }

    @Test
    fun `TCPClient receives mapped modeOverride`() {
        val modes = mapOf(
            "full" to InterfaceMode.FULL,
            "gateway" to InterfaceMode.GATEWAY,
            "access_point" to InterfaceMode.ACCESS_POINT,
            "roaming" to InterfaceMode.ROAMING,
            "boundary" to InterfaceMode.BOUNDARY,
            "internal" to InterfaceMode.POINT_TO_POINT // fallback
        )

        modes.forEach { (modeString, expectedEnum) ->
            val config = InterfaceConfig.TCPClient(
                name = "tcp_$modeString",
                targetHost = "127.0.0.1",
                targetPort = 1234,
                mode = modeString
            )
            
            NativeInterfaceFactory.restartInterface(config)
            
            val iface = NativeInterfaceFactory.currentInterfaces.find { it.name == config.name }
            requireNotNull(iface) { "Interface was not created for mode $modeString" }
            
            assertEquals("Mode mismatch for $modeString", expectedEnum, iface.modeOverride)
            NativeInterfaceFactory.shutdownAll()
        }
    }
}
