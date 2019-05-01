package com.vti.base.functional

interface OnItemClickListener<MODEL> {
    fun onItemClick(model: MODEL, type: Int = -1)
}