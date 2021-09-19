package com.example.androidtask.network.view

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.network.model.FilesListResponseItem
import java.io.File
import java.util.Collections.emptyList


class FilesListAdapter(var context: Context, itemClickInterface: ItemClickInterface) :
    RecyclerView.Adapter<FilesListAdapter.MainViewHolder>() {
    private val TAG = "PostListAdapter"
    var itemClickInterface: ItemClickInterface
    lateinit var fileName: String

    private var list: List<FilesListResponseItem> = emptyList<FilesListResponseItem>()

    init {
        this.context = context
        this.itemClickInterface = itemClickInterface
    }

    fun setChecked(position: Int) {
        list.get(position).isDownloadCompleted = true
        notifyItemChanged(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.files_list_item, parent, false)
        return MainViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItems(list.get(position))

        holder.itemView.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: " + "item clicked")
            itemClickInterface.downloadFile(list.get(position), position)

        }

        var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (list.get(position).type.equals("PDF")) {
            fileName = list.get(position).name + ".pdf"
        } else {
            fileName = list.get(position).name + ".mp4"
        }
        Log.d(TAG, "onBindViewHolder file ex: " + path + "/" + fileName)


        if (list.get(position).isDownloadCompleted || fileExist(path.toString() + "/" + fileName) == true) {
            holder.imgCheckMark.visibility = View.VISIBLE

        } else {
            holder.imgCheckMark.visibility = View.GONE
        }

    }


    fun setAdapterList(list: List<FilesListResponseItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun fileExist(filePath: String?): Boolean {

        var file = File(filePath)
        return file.exists()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tFileName: TextView;
        lateinit var imgCheckMark: ImageView;
        fun bindItems(filesListResponseItem: FilesListResponseItem) {
            tFileName = itemView.findViewById(R.id.tFileName)

            imgCheckMark = itemView.findViewById(R.id.imgCheckMark)
            tFileName.text = filesListResponseItem.name


        }


    }


    interface ItemClickInterface {
        fun downloadFile(filesListResponseItem: FilesListResponseItem, position: Int)
    }
}