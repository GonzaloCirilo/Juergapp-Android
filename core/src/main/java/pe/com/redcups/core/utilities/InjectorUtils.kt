package pe.com.redcups.core.utilities

import android.content.Context
import pe.com.redcups.core.JuergappDatabase
import pe.com.redcups.core.repository.*
import pe.com.redcups.core.viewmodel.UserProfileViewModelFactory
import pe.com.redcups.core.viewmodel.events.EventAddViewModelFactory
import pe.com.redcups.core.viewmodel.events.EventDetailViewModelFactory
import pe.com.redcups.core.viewmodel.events.EventViewModelFactory
import pe.com.redcups.core.viewmodel.games.GameDetailViewModelFactory
import pe.com.redcups.core.viewmodel.games.GameViewModelFactory
import pe.com.redcups.core.viewmodel.products.ProductCategoryViewModelFactory
import pe.com.redcups.core.viewmodel.products.ProductDetailViewModelFactory
import pe.com.redcups.core.viewmodel.products.ProductViewModelFactory

object InjectorUtils{

    private fun getEventRepository(context: Context): EventRepository{
        return EventRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).eventDao(), context
        )
    }

    private fun getProductRepository(context: Context): ProductRepository{
        return ProductRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).productDao(), context
        )
    }

    private fun getProductCategoryRepository(context: Context): ProductCategoryRepository{
        return ProductCategoryRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).productCategoryDao(), context
        )
    }

    private fun getGameRepository(context: Context): GameRepository{
        return GameRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).gameDao(), context
        )
    }
    private fun getUserRepository(context: Context): UserRepository{
        return UserRepository.getInstance(
            JuergappDatabase.getInstance(context.applicationContext).userDao(), context
        )
    }

    fun provideEventAddViewModelFactory(context: Context): EventAddViewModelFactory{
        val repository = getEventRepository(context)
        return EventAddViewModelFactory(repository)
    }

    fun provideGameDetailViewModelFactory(
        context: Context,
        gameId: String): GameDetailViewModelFactory {
        val repository = getGameRepository(context)
        return GameDetailViewModelFactory(repository, gameId)
    }

    fun provideGameViewModelFactory(context: Context): GameViewModelFactory {
        val repository = getGameRepository(context)
        return GameViewModelFactory(repository)
    }

    fun provideProductCategoryViewModelFactory(context: Context): ProductCategoryViewModelFactory {
        val repository = getProductCategoryRepository(context)
        return ProductCategoryViewModelFactory(repository)
    }
    fun provideProductDetailViewModelFactory(context: Context,
                                             productId: String): ProductDetailViewModelFactory {
        val repository = getProductRepository(context)
        return ProductDetailViewModelFactory(repository, productId)
    }

    fun provideProductViewModelFactory(context: Context, productCategoryId: String): ProductViewModelFactory {
        val repository = getProductRepository(context)
        return ProductViewModelFactory(repository, productCategoryId)
    }

    fun provideEventViewModelFactory(context: Context): EventViewModelFactory {
        val repository = getEventRepository(context)
        return EventViewModelFactory(repository)
    }

    fun provideEventDetailViewModelFactory(
        context: Context,
        eventId: String): EventDetailViewModelFactory {
        return EventDetailViewModelFactory(getEventRepository(context), eventId)
    }

    fun provideUserViewModelFactory(context: Context,
                                    userId: String): UserProfileViewModelFactory {
        val repository = getUserRepository(context)
        return UserProfileViewModelFactory(repository, userId)
    }
}