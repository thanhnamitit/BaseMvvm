package com.vti.base.adapter

interface AdapterCommander<MODEL> {
    fun addListOfModelAtLast(models: List<MODEL>)
    fun addListOfModelAtFirst(models: List<MODEL>)
    fun addListOfModel(position: Int, models: List<MODEL>)
    fun addModelAtFirst(model: MODEL)
    fun addModelAtLast(model: MODEL)
    fun addModel(position: Int, model: MODEL)
    fun removeModel(model: MODEL)
    fun removeModelAt(position: Int)
}