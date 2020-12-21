package id.unlink.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import id.unlink.newsapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.nav_fragment))
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_fragment).navigateUp()

    companion object{
        val BASE_URL = "https://newsapi.org/"
        val CATEGORY_GENERAL = "general"
        val COUNTRY_ID = "id"
        val Q_COVID = "covid"
        val API_KEY = "REPLACE_WITH_YOUR_API_KEY"
        val TAG = "NEWS API"
    }
}