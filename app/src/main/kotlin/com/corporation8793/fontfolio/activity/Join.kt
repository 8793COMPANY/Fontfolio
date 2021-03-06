package com.corporation8793.fontfolio.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.corporation8793.fontfolio.BuildConfig
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.login.LoginActivity
import kotlinx.coroutines.*

class Join : AppCompatActivity() {
    val PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_MEDIA_LOCATION
        )
    } else {
        arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_MEDIA_LOCATION
        )
    }
    val LOCATION_PERMISSION_REQUEST_CODE = PERMISSIONS.size
    var p_list = mutableListOf<Boolean>()
    lateinit var fontfolio : Fontfolio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        fontfolio = Fontfolio().getInstance(applicationContext)

        val sign_up_btn : LinearLayout = findViewById(R.id.sign_up_btn)
        val log_in_btn : LinearLayout = findViewById(R.id.log_in_btn)
        val take_to_experience : LinearLayout = findViewById(R.id.take_to_experience)

        fontfolio.xlsToRoom()

        val job = CoroutineScope(Dispatchers.IO).launch {
            fun getList() : List<Font> {
                return fontfolio.db.fontDao().getAll()
            }

            Log.e("Join", "list initialize")
            val a_list = async { getList() }
            Fontfolio.list = a_list.await()
        }
        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)

        take_to_experience.setOnClickListener {
            if (job.isCompleted) {
                fontfolio.moveToActivity(this, MainActivity::class.java, false)
            }
        }

        sign_up_btn.setOnClickListener {
            fontfolio.moveToActivity(this, SignUp::class.java, false)
        }

        log_in_btn.setOnClickListener {
            fontfolio.moveToActivity(this, LoginActivity::class.java, false)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val job = CoroutineScope(Dispatchers.IO).launch {
            Log.e("Join", "list initialize")
            fun getList() : List<Font> {
                return fontfolio.db.fontDao().getAll()
            }

            Log.e("Join", "list initialize")
            val a_list = async { getList() }
            Fontfolio.list = a_list.await()
        }

        if(grantResults.isNotEmpty()) {
            for (p in permissions) {
                p_list.add(ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED)
            }

            if(p_list.contains(false)) {
                p_list = mutableListOf()
                ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }
}