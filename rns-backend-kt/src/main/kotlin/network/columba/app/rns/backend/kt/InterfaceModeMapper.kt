package network.columba.app.rns.backend.kt

import android.util.Log

internal fun mapInterfaceMode(modeString: String): network.reticulum.common.InterfaceMode {
    return when (modeString.lowercase()) {
        "full" -> network.reticulum.common.InterfaceMode.FULL
        "gateway" -> network.reticulum.common.InterfaceMode.GATEWAY
        "access_point" -> network.reticulum.common.InterfaceMode.ACCESS_POINT
        "roaming" -> network.reticulum.common.InterfaceMode.ROAMING
        "boundary" -> network.reticulum.common.InterfaceMode.BOUNDARY
        "internal" -> {
            Log.w("InterfaceModeMapper", "INTERNAL mode not yet supported by reticulum-kt, falling back to POINT_TO_POINT")
            network.reticulum.common.InterfaceMode.POINT_TO_POINT // fallback to the stray value or FULL
        }
        else -> network.reticulum.common.InterfaceMode.FULL
    }
}
