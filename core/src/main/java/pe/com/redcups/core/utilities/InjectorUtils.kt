package pe.com.redcups.core.utilities

import android.content.Context
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.repository.EventRepository
import pe.com.redcups.core.repository.GameRepository
import pe.com.redcups.core.repository.ProductCategoryRepository
import pe.com.redcups.core.repository.ProductRepository
import pe.com.redcups.core.viewmodel.*

object InjectorUtils{

    private fun getEventRepository(context: Context): EventRepository{
        return EventRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).eventDao()
        )
    }

    private fun getProductRepository(context: Context): ProductRepository{
        return ProductRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).productDao()
        )
    }

    private fun getProductCategoryRepository(context: Context): ProductCategoryRepository{
        return ProductCategoryRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).productCategoryDao()
        )
    }

    private fun getGameRepository(context: Context): GameRepository{
        return GameRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).gameDao()
        )
    }

    fun provideGameDetailViewModelFactory(
        context: Context,
        gameId: String): GameDetailViewModelFactory{
        val repository = getGameRepository(context)
        return GameDetailViewModelFactory(repository, gameId)
    }

    fun provideGameViewModelFactory(context: Context): GameViewModelFactory{
        val repository = getGameRepository(context)
        return GameViewModelFactory(repository)
    }

    fun provideProductCategoryViewModelFactory(context: Context): ProductCategoryViewModelFactory{
        val repository = getProductCategoryRepository(context)
        return ProductCategoryViewModelFactory(repository)
    }

    fun provideProductViewModelFactory(context: Context): ProductViewModelFactory{
        val repository = getProductRepository(context)
        return  ProductViewModelFactory(repository)
    }

    fun provideEventViewModelFactory(context: Context): EventViewModelFactory{
        val repository = getEventRepository(context)
        return EventViewModelFactory(repository)
    }

    fun provideEventDetailViewModelFactory(
        context: Context,
        eventId: String): EventDetailViewModelFactory{
        return EventDetailViewModelFactory(getEventRepository(context), eventId)
    }
}