package com.sunjoolee.sparta_week4_selfintroductionapp.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sunjoolee.sparta_week4_selfintroductionapp.R
import com.sunjoolee.sparta_week4_selfintroductionapp.user_info.UserInfoManager
import kotlin.random.Random


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"

    companion object{
        val EXTRA_NAME = "extra_name"
        val EXTRA_PASSWORD = "extra_password"

        fun newIntent(context: Context, name:String, password:String): Intent =
            Intent(context, HomeActivity::class.java).apply {
                putExtra(EXTRA_NAME, name)
                putExtra(EXTRA_PASSWORD, password)
            }
    }


    private val homeImageView:ImageView by lazy{findViewById(R.id.iv_home)}
    private val imageButton:Button by lazy{findViewById(R.id.btn_image)}

    private val nameTextView:TextView by lazy{findViewById(R.id.tv_home_name)}
    private val emailTextView:TextView by lazy {findViewById(R.id.tv_home_email)}

    private val exitButton: Button by lazy{findViewById(R.id.btn_exit)}


    private var isImageUploaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setHomeImageViewRandom()
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d(TAG, "Photo Picker) Selected URI: $uri")
                setHomeImageView(uri)
                //버튼 상태 바꾸기
                setImageButtonUpload(false)
            } else {
                Log.d(TAG, "Photo Picker) No media selected")
            }
        }
        imageButton.setOnClickListener {
            if(!isImageUploaded){
                //사진 선택하기
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            else{
                //사진 삭제하기
                setHomeImageViewRandom()
                //버튼 상태 바꾸기
                setImageButtonUpload(true)
            }
        }

        val name = intent.getStringExtra(EXTRA_NAME)!!
        val password = intent.getStringExtra(EXTRA_PASSWORD)!!
        val email = UserInfoManager.getInstance().findUserEmail(name, password)

        nameTextView.text = resources.getString(R.string.home_name, name)
        emailTextView.text = resources.getString(R.string.home_email, email)

        exitButton.setOnClickListener {
            finish()
        }
    }

    private fun setHomeImageViewRandom(){
        val imageNumber:Int = Random.nextInt(5)+1
        val imageId = resources.getIdentifier("ham${imageNumber}", "drawable", packageName)
        homeImageView.setImageResource(imageId)
    }
    private fun setHomeImageView(uri:Uri){
        homeImageView.setImageURI(uri)
    }

    private fun setImageButtonUpload(flag:Boolean){
        if(flag) {
            isImageUploaded = false
            imageButton.text = getString(R.string.upload_image)
        }
        else{
            isImageUploaded = true
            imageButton.text = getString(R.string.delete_image)
        }
    }
}