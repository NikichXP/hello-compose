package com.nikichxp.hellocompose

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }
}

@Preview
@Composable
fun Greeting() {
    val count = remember { mutableStateOf(0) }
    val fileList = remember {
        mutableStateOf(File.listRoots())
    }

    val emptyDirToast = Toast.makeText(LocalContext.current, "empty dir", Toast.LENGTH_SHORT)
    val gotPermissionToast =
        Toast.makeText(LocalContext.current, "permission OK", Toast.LENGTH_SHORT)
    val notGotPermissionToast =
        Toast.makeText(LocalContext.current, "permission FAIL", Toast.LENGTH_SHORT)

    val permissions = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE
    )

    val permissionLaunchers = permissions.map {
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                gotPermissionToast.show()
            } else {
                notGotPermissionToast.show()
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        // todo permission getter
//        Button(onClick = { requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }) {
//            Text("get permission")
//        }

        Text("Hello world!")
        Text("one more")

        Button(onClick = { count.value++ }) {
            Text("hello counter!")
        }

        Text("I've been clicked ${count.value} times")

        fileList.value.forEach {
            Text("file: ${it.absolutePath}")
            Button(onClick = {
                it.listFiles()?.also { files ->
                    fileList.value = files
                } ?: emptyDirToast.show()
            }) {
                Text(text = it.absolutePath)
            }
        }

    }
}


