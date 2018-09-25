package com.smart.recycler.modules.swipe

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.smart.adapter.recyclerview.headerfooter.HeaderFooterAdapter
import com.smart.recycler.R
import com.smart.recycler.TestViewModel
import com.smart.recycler.modules.swipe.repo.UserRepository
import com.smart.recycler.modules.swipe.repo.db.User
import com.smart.swiperefresh.SwipeRefreshRecyclerView
import com.smart.swiperefresh.view.empty.EmptyViewLayout
import java.util.*

class SwipeRefreshViewKtActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var testViewModel: TestViewModel

    internal lateinit var rv_base_swipe_refresh: SwipeRefreshRecyclerView

    private val mLifecycleRegistry = LifecycleRegistry(this@SwipeRefreshViewKtActivity)

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_swipe_refresh)

        initToolbar()

        mLifecycleRegistry.addObserver(SimpleLifecycleObserver())

        rv_base_swipe_refresh = findViewById(R.id.rv_base_swipe_refresh) as SwipeRefreshRecyclerView
        rv_base_swipe_refresh.adapter(HeaderFooterAdapter<String>(this, R.layout.layout_list_item)
                .bindViewAndData { holder, item, position -> holder.setText(R.id.tv_data, item) })
                .refresh { postRefresh() }
                .loadMore { postLoadMore() }
                .emptyView(EmptyViewLayout.newBuilder(this)
                        .emptyText("没有记录")
                        .emptyImage(R.drawable.app_refresh_goods_0)
                        .emptyClick {
                            Toast.makeText(this@SwipeRefreshViewKtActivity, "重新加载", Toast.LENGTH_SHORT).show()
                            rv_base_swipe_refresh.doRefresh()
                        }
                )

        testViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java!!)
        testViewModel.loadDatas().observe(this, android.arch.lifecycle.Observer { it ->
            // val datas: ArrayList<String> = it as ArrayList<String>

            val dataList = ArrayList<String>()
            it?.forEach {
                dataList.add(it)
            }
            rv_base_swipe_refresh.adapter.appendDataList(dataList)
        })

        val user = User(0, "adfad", "22")
        UserRepository().insertUser(user).subscribe {
            Log.d("insertUser()", it.toString())
        }

        UserRepository().loadAllUser().subscribe {
            Log.d("loadAllUser()", it.toString())
        }
    }

    private fun postLoadMore() {
        Handler().postDelayed({
            val list = ArrayList<String>()
            val dataList = rv_base_swipe_refresh.adapter.dataList
            list.add("load more new item" + dataList.size)

            rv_base_swipe_refresh.loadMoreComplete(list)
        }, 1000)
    }

    private fun postRefresh() {
        Handler().postDelayed({
            val dataList = rv_base_swipe_refresh.adapter.dataList
            dataList.add(0, "refresh new item" + dataList.size)
            if (dataList.size > 20) dataList.clear()
            rv_base_swipe_refresh.refreshComplete(dataList)
        }, 1000)
    }

    private fun initToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "SwipeRefreshView"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            //actionBar.setHomeAsUpIndicator();
            //actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true)
        }
    }

}
