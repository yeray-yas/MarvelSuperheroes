package com.yerayyas.marvelsuperheroes.framework.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yerayyas.marvelsuperheroes.data.model.Comics
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @RelaxedMockK
    private lateinit var loadSuperheroesUseCase: LoadSuperheroesUseCase

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(loadSuperheroesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)

    }


    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at first time, get all superheroes`() = runTest {
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
        coEvery { loadSuperheroesUseCase.invoke() } returns expectedSuperheroes

        // When
        mainViewModel.onCreate()

        // Then
        assert(mainViewModel.superheroes.value == expectedSuperheroes)

    }
}