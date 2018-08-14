package com.bezzo.actors.features.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bezzo.actors.R
import com.bezzo.actors.adapter.recyclerView.ActorsRVAdapter
import com.bezzo.actors.base.BaseActivity
import com.bezzo.actors.data.model.*
import com.bezzo.actors.features.detail.DetailActivity
import com.bezzo.actors.listener.OnItemClickListener
import com.bezzo.actors.listener.OnLoadMoreListener
import com.bezzo.actors.util.constanta.AppConstans
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeContracts.View {

    @Inject
    lateinit var presenter : HomePresenter<HomeContracts.View>
    @Inject
    lateinit var linearLayoutManager : LinearLayoutManager

    var allActors = ArrayList<Actors>()
    lateinit var rvAdapter : ActorsRVAdapter

    override fun onInitializedView(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        presenter.onAttach(this)

        setActionBarTitle(getString(R.string.home))

        rvAdapter = ActorsRVAdapter(this, allActors)

        initRecyclerView()

        presenter.getActors()
        presenter.getActorsApi()

        sr_list.setOnRefreshListener {
            presenter.getActorsApi()
        }

        rvAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(itemView: View, position: Int) {
                var data = Bundle()
                data.putString(AppConstans.ACTOR, presenter.gson.toJson(allActors[position]))

                goToActivity(DetailActivity::class.java, data, false)
            }

            override fun onItemLongClick(itemView: View, position: Int): Boolean {
                return true
            }

        })
    }

    override fun hideRefreshing() {
        if (sr_list.isRefreshing){
            sr_list.isRefreshing = false
        }
    }

    fun initRecyclerView(){
        rv_list.layoutManager = linearLayoutManager
        rv_list.adapter = rvAdapter
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showAll(values: List<Actors>) {
        allActors.clear()
        allActors.addAll(values)
        rvAdapter.notifyDataSetChanged()
    }
}
