package pe.com.redcups.core

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pe.com.redcups.core.model.Game
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.Constants
import pe.com.redcups.core.network.GsonRequest
import pe.com.redcups.core.network.JuergappAPI
import java.util.concurrent.CountDownLatch

@RunWith(RobolectricTestRunner::class)

class GameUnitTest {
    private lateinit var context: Context
    private lateinit var queue: RequestQueue

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        queue = VolleyConfig.newVolleyRequestQueueForTest(context)
    }

    @Test
    fun gameRequest(){

        AppController.getInstance()

        // Given
        var game: Game? = null

        // Get Game with id =  1
        var game_id = 1;

        // When
        // Make request
        runBlocking {
            JuergappAPI.getInstance(context).getResource(Game::class.java, "/$game_id/")
        }

        // Then

        Assert.assertNotNull(game)

        // this is the default first item
        Assert.assertEquals(1, game!!.id)
        Assert.assertEquals("Alberto Grimes Sr.", game!!.name)
        Assert.assertEquals("Illum recusandae perspiciatis. Error architecto iusto. Et suscipit adipisci. Autem molestiae eaque. Impedit non numquam. Et molestiae dignissimos. Earum est corporis. Eos quam molestias. Eum explicabo quia. Quis eos nemo. Illo iusto adipisci. Rem accusamus doloremque.", game!!.description)
    }


    @Test
    fun gameListRequest(){
        AppController.getInstance()
        // Given
        var games: Array<Game> = emptyArray()
        // Make request
        runBlocking {
         JuergappAPI.getInstance(context).getResource( Array<Game>::class.java)
        }
        // Then

        Assert.assertNotNull(games)
        Assert.assertNotEquals(games.size, -1)

        //NOTE: This test fails if server returns an empty array
        Assert.assertEquals(1, games[0].id)

        // this is the default first item
        Assert.assertEquals("Alberto Grimes Sr.", games[0].name)
        Assert.assertEquals("Illum recusandae perspiciatis. Error architecto iusto. Et suscipit adipisci. Autem molestiae eaque. Impedit non numquam. Et molestiae dignissimos. Earum est corporis. Eos quam molestias. Eum explicabo quia. Quis eos nemo. Illo iusto adipisci. Rem accusamus doloremque.", games[0].description)
    }

}
