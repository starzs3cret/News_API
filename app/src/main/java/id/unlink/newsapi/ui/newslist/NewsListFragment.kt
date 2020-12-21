package id.unlink.newsapi.ui.newslist

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.unlink.newsapi.MainActivity.Companion.TAG
import id.unlink.newsapi.R
import id.unlink.newsapi.databinding.NewsListFragmentBinding
import id.unlink.newsapi.model.Article
import id.unlink.newsapi.recyclev.NewsAdapter

class NewsListFragment : Fragment() {

    companion object {
        fun newInstance() = NewsListFragment()
    }

    private lateinit var viewModel: NewsListViewModel
    private lateinit var binding: NewsListFragmentBinding
    val list = ArrayList<Article>()
    lateinit var menuItem: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.news_list_fragment,container,false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list,menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! >= 3){
                    viewModel.searchNews(query)
                } else{
                    Snackbar.make(binding.frameLayout,"Setidaknya gunakan kata lebih panjang",Snackbar.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_search->{

            }
            R.id.setting->{

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // recycleview
        val listAdapter= NewsAdapter(list)
        listAdapter.notifyDataSetChanged()

        binding.recy.setHasFixedSize(true)
        binding.recy.layoutManager = LinearLayoutManager(this.context)
        binding.recy.adapter = listAdapter

        //view model
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(NewsListViewModel::class.java)
        viewModel.setNews()
        viewModel.getNews().observe(viewLifecycleOwner, Observer { newsItem ->
            if (newsItem != null) {
                listAdapter.setData(newsItem)
                listAdapter.notifyDataSetChanged()
                // visible
                binding.recy.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.view.visibility = View.GONE
                Log.d(TAG, "onActivityCreated: ${newsItem.size}")
                if (newsItem.size==0){
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.text = "Tidak ditemukan berita..."
                }
            }
        })
        listAdapter.setOnItemCLickCallback(object : NewsAdapter.OnItemCLickCallback {
            override fun onItemClicked(data: Article, position: Int, type: String) {
                //Toast.makeText(context, "selected: $type", Toast.LENGTH_SHORT).show()
                when(type){
                    "Article"->{
                        articleAction(data,position)
                    }
                    "Share"->{
                        shareAction(data,position)
                    }
                    "Love"->{
                        loveAction(data,position)
                    }
                }
            }

        })

    }

    private fun articleAction(data: Article, position: Int) {
        Snackbar.make(binding.frameLayout,"Kamu mengeklik ${data.title}",Snackbar.LENGTH_SHORT).show()
        val browserIntent = Intent(Intent.ACTION_VIEW,Uri.parse(data.url))
        startActivity(browserIntent)
    }

    private fun shareAction(data: Article, position: Int) {
        Snackbar.make(binding.frameLayout,"Kamu membagikan ${data.title}",Snackbar.LENGTH_SHORT).show()
        val stringShare= "${data.title} \n\nbaca selengkapnya ...\n${data.url}"
        val shareIntent= Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,stringShare)
        startActivity(Intent.createChooser(shareIntent,"Bagikan ke:"))

    }

    private fun loveAction(data: Article, position: Int) {
        Snackbar.make(binding.frameLayout,"Kamu menyukai ${data.title}",Snackbar.LENGTH_SHORT).show()
    }



}


