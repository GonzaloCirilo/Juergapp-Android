package pe.com.redcups.core.utilities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object BitmapUtils {
    fun stringToBitmap(data: String): Bitmap {
        val decodedString = Base64.decode(data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

    }

}