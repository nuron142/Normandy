package com.sunil.kumar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sunil.kumar.BR

/*
 *   Base recyclerview adapter which supports databinding
 */

class BindingRecyclerAdapter : RecyclerView.Adapter<BindingHolder> {

    private val dataSet: ObservableArrayList<RecyclerViewModel>
    private val viewModelLayoutIdMap: HashMap<Class<out RecyclerViewModel>, Int>

    constructor(
        dataSet: ObservableArrayList<RecyclerViewModel>,
        viewModelLayoutIdMap: HashMap<Class<out RecyclerViewModel>, Int>
    ) {

        this.dataSet = dataSet
        this.viewModelLayoutIdMap = viewModelLayoutIdMap

        setUpListener()
    }

    override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): BindingHolder {

        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)

        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {

        holder.binding.setVariable(BR.vm, dataSet[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {

        val viewModel = dataSet[position]

        return viewModelLayoutIdMap[viewModel.javaClass] ?: -1
    }


    private fun setUpListener() {

        dataSet.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<ViewModel>>() {

            override fun onChanged(sender: ObservableList<ViewModel>) {

                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(sender: ObservableList<ViewModel>, positionStart: Int, itemCount: Int) {

                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<ViewModel>, positionStart: Int, itemCount: Int) {

                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableList<ViewModel>,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {

                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeRemoved(sender: ObservableList<ViewModel>, positionStart: Int, itemCount: Int) {

                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })

    }
}
