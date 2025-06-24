package com.pardeep.roommvvmpractice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pardeep.roommvvmpractice.data.dataBase.AppDataBase
import com.pardeep.roommvvmpractice.data.model.UserDataModel
import com.pardeep.roommvvmpractice.viewModel.HomeScreenViewModel
import com.pardeep.roommvvmpractice.viewModel.HomeScreenViewModelFactory
import com.pardeep.roommvvmpractice.viewModel.UserRepositoryImp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUi(modifier: Modifier = Modifier) {
    var showAddDialogState by remember { mutableStateOf(false) }
    var showUpdateDialogState by remember { mutableStateOf(false) }
    val appDataBase = AppDataBase.getInstance(LocalContext.current.applicationContext)
    val userRepositoryImp = UserRepositoryImp(appDataBase = appDataBase)
    val homeScreenViewModelFactory =
        HomeScreenViewModelFactory(userRepositoryImp = userRepositoryImp)
    val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = homeScreenViewModelFactory)
    val userData by homeScreenViewModel.getUserData.collectAsState()
    var selectedData by remember { mutableStateOf<UserDataModel?>(null) }
    val searchQuery by homeScreenViewModel.searchData.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAddDialogState = true
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        LaunchedEffect(Unit) {
            homeScreenViewModel.onQueryDataChange("", "All")
        }
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("All", "Male", "Female")

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        homeScreenViewModel.onQueryDataChange(
                            it,
                            genderLabel = options[selectedIndex]
                        )
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ), modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Center
                ) {
                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = {
                                    selectedIndex = index
                                    homeScreenViewModel.onQueryDataChange("", label)
                                },
                                selected = index == selectedIndex,
                                label = { Text(label) }
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(userData.size) { index ->
                        val user = userData[index]

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedData = user
                                    showUpdateDialogState = true
                                },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Name",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Text(
                                            text = user.userName,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Email",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Text(
                                            text = user.email,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    Column(modifier = Modifier.weight(0.7f)) {
                                        Text(
                                            text = "Gender",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Text(
                                            text = user.gender,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete User",
                                        modifier = Modifier
                                            .padding(start = 12.dp)
                                            .clickable {
                                                homeScreenViewModel.deleteUserData(user.userId)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }

            }

        }

        if (showAddDialogState) {
            UserInputDialog(
                isEditMode = false,
                onDismiss = { showAddDialogState = false },
                onConfirm = { name, email, gender ->
                    homeScreenViewModel.insertUserData(name, gender, email)
                },
                homeScreenViewModel = homeScreenViewModel
            )
        }

        if (showUpdateDialogState) {
            UserInputDialog(
                isEditMode = true,
                userToEdit = selectedData,
                onDismiss = { showUpdateDialogState = false },
                onConfirm = { name, email, gender ->
                    selectedData?.let {
                        homeScreenViewModel.updateUserData(it.userId, name, gender, email)
                    }
                },
                homeScreenViewModel = homeScreenViewModel
            )
        }

    }

}


@Composable
fun UserInputDialog(
    isEditMode: Boolean,
    userToEdit: UserDataModel? = null,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit,
    homeScreenViewModel: HomeScreenViewModel
) {
    // Pre-fill when editing
    LaunchedEffect(userToEdit) {
        userToEdit?.let {
            homeScreenViewModel.updateUserName(it.userName)
            homeScreenViewModel.updateEmail(it.email)
            homeScreenViewModel.updateGender(it.gender)
        } ?: run {
            homeScreenViewModel.updateUserName("")
            homeScreenViewModel.updateEmail("")
            homeScreenViewModel.updateGender("Male")
        }
    }
    val username by homeScreenViewModel.userName.collectAsState()
    val userEmail by homeScreenViewModel.userEmail.collectAsState()
    val userGender by homeScreenViewModel.userGender.collectAsState()

    val genderOptions = listOf("Male", "Female")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(userToEdit?.gender ?: genderOptions[0])
    }



    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (isEditMode) "Update User" else "Add User")

                OutlinedTextField(
                    value = username,
                    onValueChange = { homeScreenViewModel.updateUserName(it) },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = userEmail,
                    onValueChange = {
                        homeScreenViewModel.updateEmail(it)
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                genderOptions.forEach { gender ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedGender == gender,
                            onClick = {
                                onGenderSelected(gender)
                                homeScreenViewModel.updateGender(gender)
                            }
                        )
                        Text(text = gender)
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        onConfirm(username, userEmail, userGender)
                        onDismiss()
                    }) {
                        Text(text = if (isEditMode) "Update" else "Add")
                    }

                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ShowPrev() {
    HomeUi()
}