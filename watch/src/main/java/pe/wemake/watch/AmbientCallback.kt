package pe.wemake.watch

import android.os.Bundle
import androidx.wear.ambient.AmbientModeSupport

class AmbientCallback: AmbientModeSupport.AmbientCallback() {
    override fun onEnterAmbient(ambientDetails: Bundle?) {
        // Handle entering ambient mode
    }

    override fun onExitAmbient() {
        // Handle exiting ambient mode
    }

    override fun onUpdateAmbient() {
        // Update the content
    }

}