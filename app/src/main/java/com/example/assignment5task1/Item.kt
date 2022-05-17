package com.example.assignment5task1

data class Item(val id: Int,
    var name:String, var details:String, var qty:Int,
    var size:String, var urgent:Int,
    var bought:Int, var dateBought:String){
    constructor(id:Int, name: String,details: String,qty: Int,size: String,urgent: Int,bought: Int):this(id,name,details,qty,size,urgent,bought,"")
    constructor(name: String,details: String,qty: Int,size: String,urgent: Int,bought: Int,dateBought: String):this(-1,name,details,qty,size,urgent,bought,dateBought)
    constructor(name: String,details: String,qty: Int,size: String,urgent: Int,bought: Int):this(-1,name,details,qty,size,urgent,bought,"")

    override fun toString(): String {
        return "Item(id=$id, name='$name', details='$details', qty=$qty, size='$size', urgent=$urgent, bought=$bought, dateBought='$dateBought')"
    }


}
