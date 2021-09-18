package com.example.androidtask.network.view

import android.app.DownloadManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R

import com.example.androidtask.network.model.FilesListResponse
import com.example.androidtask.network.model.FilesListResponseItem
import com.example.androidtask.network.viewmodel.RetroViewModel
import com.example.androidtask.network.viewmodel.RetroViewModelFactory

import kotlinx.android.synthetic.main.post_list_layout.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class RetroFragment : Fragment(), PostListAdapter.ItemClickInterface {

    lateinit var retroViewModel: RetroViewModel
    var fragmentView: View? = null
    private var listAdapter: PostListAdapter? = null


    lateinit var videoURL: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView= inflater.inflate(R.layout.post_list_layout, container, false)

        initAdapter()
        setAdapter()
        fetchRetroInfo()
        return fragmentView
    }

    fun initViewModel() {
        var retroViewModelFactory = RetroViewModelFactory()
        retroViewModel = ViewModelProviders.of(this, retroViewModelFactory).get(RetroViewModel::class.java)
    }

    fun fetchRetroInfo() {
        retroViewModel.postInfoLiveData?.observe(viewLifecycleOwner, object : Observer<FilesListResponse> {
            override fun onChanged(t: FilesListResponse?) {
                t?.apply {
                    listAdapter?.setAdapterList(t)
                }


            }
        })
    }

    private fun setAdapter() {
        fragmentView?.post_list?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = listAdapter
        }

    }

    private fun initAdapter() {
        listAdapter = PostListAdapter(this@RetroFragment.requireActivity(), this)
    }

    override fun downloadFile(filesListResponseItem: FilesListResponseItem) {
        if (filesListResponseItem.type.equals("VIDEO")) {
            this.videoURL=filesListResponseItem.url
            val vdoUri = videoURL
            try {
                val request = DownloadManager.Request(Uri.parse(vdoUri))
                request.setDescription("download")
                request.setTitle("myvideo")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner()
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                }
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM, "myvideo" + ".mp4")
                val manager = activity!!.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
                manager.enqueue(request)
            } catch (ex: Exception) {
            }
        }
    }




    private inner class Download : AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            val vidurl = videoURL
            downloadfile(vidurl)
            return null
        }
    }


    private fun downloadfile(vidurl: String) {
        val sd = SimpleDateFormat("yymmhh")
        val date = sd.format(Date())
        val name = "video$date.mp4"
        try {
            val rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator + "My_Video"
            val rootFile = File(rootDir)
            rootFile.mkdir()
            val url = URL(videoURL)
            val c = url.openConnection() as HttpURLConnection
            c.requestMethod = "GET"
            c.doOutput = true
            c.connect()
            val f = FileOutputStream(File(rootFile,
                    name))
            val `in` = c.inputStream
            val buffer = ByteArray(1024)
            var len1 = 0
            while (`in`.read(buffer).also { len1 = it } > 0) {
                f.write(buffer, 0, len1)
            }
            f.close()
        } catch (e: IOException) {
            Log.d("Error....", e.toString())
        }
    }




}