package id.unlink.newsapi.recyclev

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.unlink.newsapi.MainActivity.Companion.TAG
import id.unlink.newsapi.R
import id.unlink.newsapi.model.Article

class NewsAdapter(private val listNews: ArrayList<Article>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // click listener

    public var TYPED = arrayOf("Article","Share","Love")

    private var OnItemClickCallback: OnItemCLickCallback? = null

    interface OnItemCLickCallback {
        fun onItemClicked(data: Article, position: Int,type: String)

    }

    fun setOnItemCLickCallback(onItemCLickCallback: OnItemCLickCallback) {
        this.OnItemClickCallback = onItemCLickCallback
    }


    fun setData(items: ArrayList<Article>) {
        listNews.clear()
        listNews.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val mod = position % 2
        //Log.d(TAG, "getItemViewType: mod "+mod)
        return mod
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Log.d(TAG, "onCreateViewHolder: viewtype"+viewType)
        return when (viewType) {
            0 -> NewsViewHolder0(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_small, parent, false)
            )
            1 -> NewsViewHolder1(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_medium, parent, false)
            )
            else -> NewsViewHolder0(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_medium, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                val holder0 = holder as NewsViewHolder0
                holder0.bind(listNews[position], position)
            }
            1 -> {
                val holder1 = holder as NewsViewHolder1
                holder1.bind(listNews[position], position)
            }
        }

    }

    inner class NewsViewHolder0(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // small
        fun bind(article: Article, position: Int) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                    .into(itemView.findViewById(R.id.imNews))
                itemView.findViewById<TextView>(R.id.txTitle).text = article.title
                itemView.findViewById<TextView>(R.id.txDesc).text = article.description
                itemView.findViewById<TextView>(R.id.txSource).text = article.source.name
                itemView.findViewById<TextView>(R.id.txPublishat).text = article.publishedAt
                // button listener
                itemView.setOnClickListener{ OnItemClickCallback?.onItemClicked(article,position,TYPED[0])}
                itemView.findViewById<ImageButton>(R.id.btShare)
                    .setOnClickListener { OnItemClickCallback?.onItemClicked(article, position,TYPED[1]) }
                itemView.findViewById<ImageButton>(R.id.btLove)
                    .setOnClickListener { OnItemClickCallback?.onItemClicked(article, position,TYPED[2]) }
            }
        }
    }

    inner class NewsViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // mediom
        fun bind(article: Article, position: Int) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .into(itemView.findViewById(R.id.imNews2))
                itemView.findViewById<TextView>(R.id.txTitle2).text = article.title
                itemView.findViewById<TextView>(R.id.txDesc2).text = article.description
                itemView.findViewById<TextView>(R.id.txSource2).text = article.source.name
                itemView.findViewById<TextView>(R.id.txPublishat2).text = article.publishedAt
                // button listener
                itemView.setOnClickListener{ OnItemClickCallback?.onItemClicked(article,position,TYPED[0])}
                itemView.findViewById<ImageButton>(R.id.btShare2)
                    .setOnClickListener { OnItemClickCallback?.onItemClicked(article, position,TYPED[1]) }
                itemView.findViewById<ImageButton>(R.id.btLove2)
                    .setOnClickListener { OnItemClickCallback?.onItemClicked(article, position,TYPED[2]) }
            }
        }
    }

}