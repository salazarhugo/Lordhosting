package com.salazar.lordhosting.auth.domain.usecase

import com.salazar.lordhosting.core.data.datastore.DataStoreRepositoryImpl
import com.salazar.lordhosting.core.di.IODispatcher
import com.salazar.lordhosting.server.data.repository.ServerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithApiKeyUseCase @Inject constructor(
    private val datastore: DataStoreRepositoryImpl,
    private val serverRepository: ServerRepository,
    @IODispatcher val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        apiKey: String,
    ): Result<Unit> = withContext(ioDispatcher) {
        val formattedApiKey = apiKey
            .replace("\n", "")
            .replace("\r", "")
        datastore.updateApiKey(apiKey = formattedApiKey)
        return@withContext serverRepository.listServers().map {}
    }
}
