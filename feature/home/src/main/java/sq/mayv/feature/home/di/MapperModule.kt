package sq.mayv.feature.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sq.mayv.feature.home.mapper.GenresModelUIMapper
import sq.mayv.feature.home.mapper.MoviesModelUIMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideMoviesModelUIMapper(): MoviesModelUIMapper {
        return MoviesModelUIMapper
    }

    @Provides
    @Singleton
    fun provideGenresModelUIMapper(): GenresModelUIMapper {
        return GenresModelUIMapper
    }

}

