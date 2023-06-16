package com.yerayyas.marvelsuperheroes.usecases

import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import com.yerayyas.marvelsuperheroes.domain.model.Comics
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.model.Thumbnail
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import com.yerayyas.marvelsuperheroes.framework.states.Failure
import com.yerayyas.marvelsuperheroes.framework.states.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadSuperheroesUseCaseTest {

    private lateinit var mockRepository: SuperheroRepository

    private lateinit var useCase: LoadSuperheroesUseCase

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = LoadSuperheroesUseCase(mockRepository)
    }

    @Test
    fun `invoke should return list of superheroes`() = runBlocking {
        // Arrange
        val expectedSuperheroes =  listOf(
            Superhero(
                comics = Comics(1), "superhero mix 1",
                56, "SuperMan", thumbnail = Thumbnail("www.google.superman", "jpg")
            ),
            Superhero(
                comics = Comics(4), "superhero mix 2",
                57, "Captain America", thumbnail = Thumbnail("www.google.captainamerica", "png")
            ),
            Superhero(
                comics = Comics(6), "superhero mix 3",
                58, "Thor", thumbnail = Thumbnail("www.google.thor", "jpeg")
            )
        )
        coEvery { mockRepository.getSuperheroes() } returns expectedSuperheroes

        // Act
        val result: Flow<Result<List<Superhero>, Failure>> = useCase.invoke()

        // Assert
        val resultList = result.take(2).toList()
        assertEquals(Result.Success(expectedSuperheroes), resultList.last())

    }
}




