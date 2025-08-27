package sq.mayv.data.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sq.mayv.data.remote.datasource.IRemoteDataSource
import sq.mayv.data.remote.datasource.RemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        dataSource: RemoteDataSource
    ): IRemoteDataSource

}