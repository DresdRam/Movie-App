package sq.mayv.data.local.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sq.mayv.data.local.mapper.GenresEntityModelMapper
import sq.mayv.data.local.mapper.MoviesEntityModelMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideMoviesEntityToModelMapper(): MoviesEntityModelMapper {
        return MoviesEntityModelMapper
    }

    @Provides
    @Singleton
    fun provideGenresEntityToModelMapper(): GenresEntityModelMapper {
        return GenresEntityModelMapper
    }

}

