package com.smart.recycler.modules.swipe

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.smart.adapter.recyclerview.headerfooter.HeaderFooterAdapter
import com.smart.recycler.R
import com.smart.recycler.TestViewModel
import com.smart.swiperefresh.SwipeRefreshRecyclerView
import com.smart.swiperefresh.view.empty.EmptyViewLayout
import java.util.*

class SwipeRefreshViewKtActivity : AppCompatActivity(), LifecycleOwner {

    private val mDatas = arrayOf(
            "Adapter: A subclass of RecyclerView.Adapter responsible for providing views that represent items in a data set.",
            "Position: The position of a data item within an Adapter.",
            "Index: The index of an attached child view as used in a call to getChildAt(int). Contrast with Position.",
            "Binding: The process of preparing a child view to display data corresponding to a position within the adapter.",
            "Recycle (view): A view previously used to display data for a specific adapter position may be " +
                    "placed in a cache for later reuse to display the same type of data again later. " +
                    "This can drastically improve performance by skipping initial layout inflation or construction",
            "Scrap (view): A child view that has entered into a temporarily detached state during layout. " +
                    "Scrap views may be reused without becoming fully detached from the parent RecyclerView, " +
                    "either unmodified if no rebinding is required or modified by the adapter if the view was considered dirty.",
            "Dirty (view): A child view that must be rebound by the adapter before being displayed.",
            "hehe",
            "495948",
            "89757",
            "66666")
    private val dataList = ArrayList<String>()

    private lateinit var testViewModel: TestViewModel

    internal lateinit var rv_base_swipe_refresh: SwipeRefreshRecyclerView

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    private val mLifecycleRegistry = LifecycleRegistry(this@SwipeRefreshViewKtActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_swipe_refresh)

        initToolbar()

        mLifecycleRegistry.addObserver(SimpleLifecycleObserver())

//        dataList.addAll(Arrays.asList(*mDatas))

        rv_base_swipe_refresh = findViewById(R.id.rv_base_swipe_refresh) as SwipeRefreshRecyclerView
        rv_base_swipe_refresh.adapter(HeaderFooterAdapter(this, R.layout.layout_list_item, dataList)
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

        // 手动调用一次刷新
        rv_base_swipe_refresh.doRefresh()

        testViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java!!)
        testViewModel.loadDatas().observe(this, android.arch.lifecycle.Observer { it ->

            // val datas: ArrayList<String> = it as ArrayList<String>
            it?.forEach {
                dataList.add(it)
            }

            rv_base_swipe_refresh.adapter.notifyDataChanged()
        })

    }

    private fun postLoadMore() {
        Handler().postDelayed({
            val list = ArrayList<String>()
            list.add("load more new item" + dataList.size)

            rv_base_swipe_refresh.loadMoreComplete(list)
        }, 1000)
    }

    private fun postRefresh() {
        Handler().postDelayed({
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
