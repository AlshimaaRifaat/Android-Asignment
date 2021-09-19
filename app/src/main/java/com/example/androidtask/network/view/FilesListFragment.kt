package com.example.androidtask.network.view


import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.network.model.FilesListResponse
import com.example.androidtask.network.model.FilesListResponseItem
import com.example.androidtask.network.viewmodel.FilesListViewModel
import com.example.androidtask.network.viewmodel.FilesListViewModelFactory
import kotlinx.android.synthetic.main.fragment_files_list.view.*
import java.io.File

import java.util.*

class FilesListFragment : Fragment(), FilesListAdapter.ItemClickInterface {
    private val TAG = "RetroFragment"
    lateinit var filesListViewModel: FilesListViewModel
    var fragmentView: View? = null
    private var filesListAdapter: FilesListAdapter? = null


    lateinit var videoURL: String
    lateinit var videoName: String

    var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_files_list, container, false)

        initAdapter()
        setAdapter(fragmentView!!)
        fetchFilesListInfo()
        return fragmentView
    }

    fun initViewModel() {
        var filesListViewModelFactory = FilesListViewModelFactory()
        filesListViewModel =
            ViewModelProviders.of(this, filesListViewModelFactory).get(FilesListViewModel::class.java)
    }

    fun fetchFilesListInfo() {
        if (isConnected) {
            filesListViewModel.postInfoLiveData?.observe(
                viewLifecycleOwner,
                object : Observer<FilesListResponse> {
                    override fun onChanged(t: FilesListResponse?) {
                        t?.apply {
                            filesListAdapter?.setAdapterList(t)
                        }


                    }
                })
        }else{
            Toast.makeText(context, R.string.Check_network_connection, Toast.LENGTH_LONG).show()
        }
    }

    val isConnected:Boolean
        get() {
            return (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

    private fun setAdapter(view: View) {
        view?.rvFilesList?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = filesListAdapter
        }

    }

    private fun initAdapter() {
        filesListAdapter = FilesListAdapter(this@FilesListFragment.requireActivity(), this)
    }

    override fun downloadFile(filesListResponseItem: FilesListResponseItem, position: Int) {
        if (filesListResponseItem.type.equals("VIDEO")) {
            this.videoURL = filesListResponseItem.url
            this.videoName = filesListResponseItem.name
            val vdoUri = videoURL
            this.position = position
            try {
                val request = DownloadManager.Request(Uri.parse(vdoUri))
                request.setDescription("download")
                request.setTitle(videoName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner()
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    Log.d(TAG, "downloadFile: " + "done")


                }
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    videoName + ".mp4"
                )
                val manager =
                    activity!!.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
                manager.enqueue(request)
            } catch (ex: Exception) {
            }

            onDownloadComplete()
        } else if (filesListResponseItem.type.equals("PDF")) {

            DownloadPDF(filesListResponseItem.url, filesListResponseItem.name, position)
            onDownloadComplete()
        }
    }

    private fun DownloadPDF(url: String, name: String, position: Int) {
        this.position = position
        val request = DownloadManager.Request(Uri.parse(url))
        //val tempTitle: String = name.replace("", "_")
        request.setTitle(name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        }
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "$name.pdf"
        )
        val downloadManager =
            context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        request.setMimeType("application/pdf")
        request.allowScanningByMediaScanner()
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        downloadManager.enqueue(request)

    }



    private fun onDownloadComplete() {

        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                filesListAdapter!!.setChecked(position)
            }
        }
        context!!.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }


}