package com.example.androidtask.network.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.androidtask.R

import com.example.androidtask.network.model.FilesListResponseItem

import java.util.Collections.emptyList


class PostListAdapter(var context: Context, itemClickInterface:ItemClickInterface):
    RecyclerView.Adapter<PostListAdapter.MainViewHolder>() {
    private  val TAG = "PostListAdapter"
    lateinit var itemClickInterface :ItemClickInterface

    private  var list: List<FilesListResponseItem> = emptyList<FilesListResponseItem>()

    init {
        this.itemClickInterface = itemClickInterface


    }
     fun setChecked(position:Int){
         list.get(position).isChecked=true
         notifyItemChanged(position)
     }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return MainViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItems(list.get(position))

        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: "+"item clicked")

            itemClickInterface.downloadFile(list.get(position),position)
        }

        if(list.get(position).isChecked){
            Log.d(TAG, "onBindViewHolder: "+"completed")
            holder.imgCheckMark.visibility=View.VISIBLE

        }else{
            holder.imgCheckMark.visibility=View.GONE
        }

    }

    fun setAdapterList(list: List<FilesListResponseItem> ){
        this.list = list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {

        return list!!.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tFileName :TextView;
        lateinit var imgCheckMark :ImageView;
        fun bindItems(filesListResponseItem: FilesListResponseItem) {
             tFileName  = itemView.findViewById(R.id.tFileName)
             imgCheckMark  = itemView.findViewById(R.id.imgCheckMark)
            tFileName.text =filesListResponseItem.name

        }



    }


    interface ItemClickInterface {
        fun downloadFile(filesListResponseItem: FilesListResponseItem,position:Int)
    }
}