package com.example.androidtask.network.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.databinding.PostListLayoutBinding
import com.example.androidtask.network.model.PostInfo
import com.example.androidtask.network.viewmodel.RetroViewModel
import com.example.androidtask.network.viewmodel.RetroViewModelFactory

import kotlinx.android.synthetic.main.post_list_layout.view.*

class RetroFragment: Fragment() {

    lateinit var retroViewModel: RetroViewModel
    var fragmentView:View?=null
    private  var listAdapter:PostListAdapter?=null
    private var  postListLayoutBinding:PostListLayoutBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        postListLayoutBinding = DataBindingUtil.inflate(inflater,
            R.layout.post_list_layout,container,false)
        fragmentView = postListLayoutBinding?.root
        initAdapter()
        setAdapter()
        fetchRetroInfo()
        return  fragmentView
    }

    fun  initViewModel(){
        var retroViewModelFactory = RetroViewModelFactory()
        retroViewModel = ViewModelProviders.of(this,retroViewModelFactory).get(RetroViewModel::class.java)
    }

    fun fetchRetroInfo(){
        retroViewModel.postInfoLiveData?.observe(viewLifecycleOwner,object:Observer<List<PostInfo>>{
            override fun onChanged(t: List<PostInfo>?) {
                t?.apply {
                    listAdapter?.setAdapterList(t)
                }


            }
        })
    }

    private fun setAdapter(){
        fragmentView?.post_list?.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = listAdapter
        }

    }

    private fun initAdapter(){
        listAdapter = PostListAdapter(this@RetroFragment.requireActivity())
    }

}