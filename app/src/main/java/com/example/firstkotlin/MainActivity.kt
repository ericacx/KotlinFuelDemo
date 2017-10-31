package com.example.firstkotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.firstkotlin.listener.OnItemClickListener
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.util.concurrent.locks.Lock

class MainActivity : AppCompatActivity() ,AnkoLogger{

    private var mContext : Context? = null
//    var datas : ArrayList<Person>? = null
    var datas : ArrayList<Catalog.Result>? = null
    var adapter : MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datas  = ArrayList<Catalog.Result>()
//        datas  = ArrayList<Person>()
//        datas!!.add(Person("rr"))
//        datas!!.add(Person("jingongmen"))
        initRecyclerView()

        info("it is a test ")
//        datas!!.get(1).name = "despitbo"

        httpConnect()
//        text.setText("helo")
//        Person("Tao").printName()

//        var quantity = 5
//        var price : Double = 20.3
//        var name : String = "大米"
//
//        println("单价： $price")
//        println("数量： $quantity")
//        println("产品： $name 总计： ${price * quantity}")
//        var x = 0
//        var  y = 5
//        var array : List<Int> = ArrayList()
//
//        if (x in 1..y-1)
//            print("OK")
//
////如果x不存在于array中，则输出Out
//        if (x !in 0..array.lastIndex) {
//            print("Out")
//        }
//
////打印1到5
//        for (x in 1..5)
//            print(x)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))//Activity的context没用，要用applicationContext
        adapter = MainAdapter(this, datas!!)
        adapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.i("mainadapter", "itemclick position --" + position)
            }

        })
        recyclerView.adapter = adapter
    }

    private fun httpConnect(){
        FuelManager.instance.basePath = "http://apis.juhe.cn"
        "/goodbook/catalog?key=fef7afbad536b0e252663dead0105a64".httpGet().responseObject { request: Request, response: Response, result: Result<Catalog, FuelError> ->
            println(request)
            println(response)
            when(result){
                is Result.Failure ->{
                    var error  = result.getAs<String>()
                    error(error)
                }
                is Result.Success -> {
                    var data : Catalog? = result.getAs<Catalog>()
                    info(data.toString())
                    dataOperation(data)
                }
            }
        }
    }

    private fun dataOperation(data: Catalog?) {
        datas!!.addAll(data!!.result!!)
        adapter!!.notifyDataSetChanged()
    }

    fun cases(obj: Any) {
        when (obj) {
            1       -> print("第一项")
            "hello" -> print("这个是字符串hello")
            is Long -> print("这是一个Long类型数据")
            !is String -> print("这不是String类型的数据")
            else    -> print("else类似于Java中的default")
        }
    }

    fun say (str : String) : String {
        return str
    }

    fun hasEmpty(vararg strArray: String?): Boolean{
        for (str in strArray){
            if ("".equals(str) || str == null)
                return true
        }
        return false
    }

     fun <T> lock(lock: Lock, body: () -> T ) : T {
         lock.lock()
         try { return body() } finally { lock.unlock() }
     }
}
