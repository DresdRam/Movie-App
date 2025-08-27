package sq.mayv.data.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sq.mayv.data.local.datasource.ILocalDataSource
import sq.mayv.data.local.datasource.LocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        dataSource: LocalDataSource
    ): ILocalDataSource

}