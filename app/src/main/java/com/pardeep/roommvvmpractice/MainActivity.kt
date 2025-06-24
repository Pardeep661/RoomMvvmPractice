package com.pardeep.roommvvmpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pardeep.roommvvmpractice.data.dataBase.AppDataBase
import com.pardeep.roommvvmpractice.ui.theme.RoomMvvmPracticeTheme
import com.pardeep.roommvvmpractice.viewModel.HomeScreenViewModel
import com.pardeep.roommvvmpractice.viewModel.HomeScreenViewModelFactory
import com.pardeep.roommvvmpractice.viewModel.UserRepositoryImp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appDatabase = AppDataBase.getInstance(applicationContext)
            val userRepositoryImp = UserRepositoryImp(appDatabase)
            val viewModelFactory = HomeScreenViewModelFactory(userRepositoryImp)

            val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = viewModelFactory)

            RoomMvvmPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  HomeUi(
                      modifier = Modifier.padding(innerPadding),
                  )
                }
            }
        }
    }
}
