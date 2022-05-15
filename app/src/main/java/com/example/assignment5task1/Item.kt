package com.example.assignment5task1

data class Item(
    var name:String, var details:String, var qty:Int,
    var size:String, var urgent:Boolean,
    var bought:Boolean, val id: Int? =null)
