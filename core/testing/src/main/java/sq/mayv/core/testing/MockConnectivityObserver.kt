package sq.mayv.core.testing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sq.mayv.core.common.ConnectivityObserver

class MockConnectivityObserver : ConnectivityObserver {

    override val isConnected: Flow<Boolean> = flow {
        emit(true)
    }
}