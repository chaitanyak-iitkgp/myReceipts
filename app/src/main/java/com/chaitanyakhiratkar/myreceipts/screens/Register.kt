import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chaitanyakhiownerPhoneNumberratkar.myreceipts.services.authApi
import com.chaitanyakhiratkar.myreceipts.components.common.ButtonCommon
import com.chaitanyakhiratkar.myreceipts.components.common.InputCommon
import com.chaitanyakhiratkar.myreceipts.services.StoreModel
import com.chaitanyakhiratkar.myreceipts.services.UserModel
import com.chaitanyakhiratkar.myreceipts.utils.showShortToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Register(navController: NavHostController) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    var userState: UserModel by remember {
        mutableStateOf(
            UserModel(
                id = "",
                name = "Chaitanya Khiratkar",
                phoneNumber = "7057868697",
                token = "",
                memberCode = "",
                memberPin = "",
                storeId = ""
            )
        )
    }
    var storeState: StoreModel by remember {
        mutableStateOf(
            StoreModel(
                id = "",
                name = "Tata",
                fullAddress = "Malviya Nagar",
                city = "Delhi",
                pincode = "445304",
                phoneNumber = "",
                gstIn = "GST101011111212",
                adminId = "",
                rootPassword = "123456",
                token = null
            )
        )
    }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    fun validateInputs(): Boolean {
        with(userState) {
            if (name.isBlank() || phoneNumber.isBlank()) {
                errorMessage = "Please fill all required user fields."
                return false
            }
            if (!Regex("\\d{10}").matches(phoneNumber)) {
                errorMessage = "Invalid phone number."
                return false
            }
        }

        with(storeState) {
            if (name.isBlank() || fullAddress.isBlank() || city.isBlank() || pincode.isBlank() || rootPassword.isBlank() || gstIn.isBlank()) {
                errorMessage = "Please fill all required store fields."
                return false
            }
            if (!Regex("\\d{6}").matches(pincode)) {
                errorMessage = "Invalid pincode."
                return false
            }
            if ( phoneNumber?.let { Regex("\\d{10}").matches(it) }!!) {
                errorMessage = "Invalid Store Phone Number."
                return false
            }
        }
        return true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register Your Store",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(10.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f) // Takes up the remaining vertical space
                .verticalScroll(scrollState), // Enables vertical scrolling
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            InputCommon(text = userState.name,
                onChange = { userState = userState.copy(name = it) },
                label = { Text("Admin Name") })

            InputCommon(
                text = userState.phoneNumber,
                onChange = { userState = userState.copy(phoneNumber = it) },
                label = { Text("Admin Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            InputCommon(text = storeState.name,
                onChange = { storeState = storeState.copy(name = it) },
                label = { Text("Store Name") })

            InputCommon(text = storeState.fullAddress,
                onChange = { storeState = storeState.copy(fullAddress = it) },
                label = { Text("Store Address") })

            InputCommon(text = storeState.city,
                onChange = { storeState = storeState.copy(city = it) },
                label = { Text("City") })

            InputCommon(
                text = storeState.pincode,
                onChange = { storeState = storeState.copy(pincode = it) },
                label = { Text("Pincode") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            InputCommon(
                text = storeState.phoneNumber ?: "",
                onChange = {
                    storeState = storeState.copy(phoneNumber = it)
                },
                label = { Text("Store Phone Number (optional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            InputCommon(text = storeState.gstIn ?: "", onChange = {
                storeState = storeState.copy(gstIn = it)

            }, label = { Text("GSTIN (optional)") })


            InputCommon(
                text = storeState.rootPassword,
                onChange = { storeState = storeState.copy(rootPassword = it) },
                label = { Text("Root Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        ButtonCommon(
            text = "Register",
            onClick = {
                if (validateInputs()) {
                    isLoading = true
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            authApi.handleRegistration(
                                context = context,
                                navController = navController,
                                store = storeState,
                                admin = userState
                            )
                        } catch (e: Exception) {
                            // Handle error (e.g., show a message to the user)
                            e.printStackTrace()
                        } finally {
                            isLoading = false // Hide loading indicator when done
                        }
                    }

                } else{
                    showShortToast(context,errorMessage)
                }
            },
            loading = isLoading
        )

    }

}


