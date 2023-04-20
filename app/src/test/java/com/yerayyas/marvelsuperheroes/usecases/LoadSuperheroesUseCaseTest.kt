package com.yerayyas.marvelsuperheroes.usecases

import com.yerayyas.marvelsuperheroes.data.model.Comics
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class LoadSuperheroesUseCaseTest {

    @Test
    fun `invoke should return superheroes from repository`() = runBlocking {
        // Given
        val expectedSuperheroes = listOf(
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
        val repository = mock(SuperheroRepository::class.java)
        `when`(repository.getSuperheroes()).thenReturn(expectedSuperheroes)
        val useCase = LoadSuperheroesUseCase(repository)

        // When
        val result = useCase.invoke()

        // Then
        assertEquals(expectedSuperheroes, result)
    }
}