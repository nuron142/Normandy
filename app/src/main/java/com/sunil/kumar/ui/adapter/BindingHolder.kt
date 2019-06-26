package com.sunil.kumar.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingHolder : RecyclerView.ViewHolder {

    var binding: ViewDataBinding

    constructor(binding: ViewDataBinding) : super(binding.root) {

        this.binding = binding
    }
}