package com.vti.base.resource.functional

interface ResourceManager {
    fun getString(id: Int): String
    fun getColor(id : Int) : Int
}