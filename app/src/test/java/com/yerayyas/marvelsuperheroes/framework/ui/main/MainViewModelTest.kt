package com.yerayyas.marvelsuperheroes.framework.ui.main


import com.yerayyas.marvelsuperheroes.domain.model.Comics
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.model.Thumbnail
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import com.yerayyas.marvelsuperheroes.framework.states.Failure
import com.yerayyas.marvelsuperheroes.framework.states.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @Mock
    private lateinit var mockLoadSuperheroesUseCase: LoadSuperheroesUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(mockLoadSuperheroesUseCase)
    }

    @Test
    fun `fetchSuperheroes should update uiState with error result`() = runBlockingTest {
        // Arrange
        val expectedError = Failure.NetworkError("Network error")
        val expectedResult = Result.Error(expectedError)
        val mockUiState = MutableStateFlow<Result<List<Superhero>, Failure>>(Result.Loading)
        `when`(mockLoadSuperheroesUseCase.invoke()).thenReturn(mockUiState)

        // Act
        viewModel.fetchSuperheroes()
        viewModel.handleFetchError(expectedError)

        // Assert
        val uiState = viewModel.uiState.first()
        assertEquals(expectedResult, uiState)
    }

    @Test
    fun `fetchSuperheroes should update uiState with loading result`() = runBlockingTest {
        // Arrange
        val expectedLoadingResult = Result.Loading
        val mockUiState = MutableStateFlow<Result<List<Superhero>, Failure>>(Result.Loading)
        `when`(mockLoadSuperheroesUseCase.invoke()).thenReturn(mockUiState)

        // Act
        viewModel.fetchSuperheroes()

        // Assert
        val uiState = viewModel.uiState.first()
        assertEquals(expectedLoadingResult, uiState)
    }

    @Test
    fun `handleFetchSuccess should update uiState with success result`() {
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
        val expectedResult = Result.Success(expectedSuperheroes)

        // Act
        viewModel.handleFetchSuccess(expectedSuperheroes)

        // Assert
        val uiState = viewModel.uiState.value
        assertEquals(expectedResult, uiState)
    }

    @Test
    fun `handleFetchError should update uiState with error result`() {
        // Arrange
        val expectedError = Failure.NetworkError("Network error")
        val expectedResult = Result.Error(expectedError)

        // Act
        viewModel.handleFetchError(expectedError)

        // Assert
        val uiState = viewModel.uiState.value
        assertEquals(expectedResult, uiState)
    }
}
