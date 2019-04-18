package com.vti.location.data.model

import android.location.Location
import com.google.android.gms.common.ConnectionResult

class LocationWrapper {


    companion object {
         const val STATUS_OK = 3816626
        fun fromLocation(location: Location): LocationWrapper {
            return LocationWrapper().apply {
                this.location = location
                this.status = STATUS_OK;
            }
        }

        fun fromStatus(errorCode: Int): LocationWrapper {
            return LocationWrapper().apply {
                location = null
                status = errorCode
            }
        }

        fun fromConnectionResult(connectionResult: ConnectionResult): LocationWrapper {
            return LocationWrapper().apply {
                this.connectionResult = connectionResult
            }
        }

    }

    var location: Location? = null
    var status = 0
    var connectionResult: ConnectionResult? = null
}
