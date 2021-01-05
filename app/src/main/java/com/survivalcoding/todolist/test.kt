package com.survivalcoding.todolist

data class Trader(val name: String, val city: String)
data class Transaction(val trader: Trader, val year: Int, val value: Int)


val transactions = listOf(
        Transaction(Trader("Brian", "Cambridge"), 2011, 300),
        Transaction(Trader("Raoul", "Cambridge"), 2012, 1000),
        Transaction(Trader("Raoul", "Cambridge"), 2011, 400),
        Transaction(Trader("Mario", "Milan"), 2012, 710),
        Transaction(Trader("Mario", "Milan"), 2012, 700),
        Transaction(Trader("Alan", "Cambridge"), 2012, 950)
)

ㅇ//
fun main()
{

    println("Answer 1 : ${transactions.sortedBy{it.value}}")
    println("Answer 2 : ${transactions.map{it.trader.city}.toSet()}")
    println("Answer 3 : ${transactions.filter{it.trader.city=="Cambridge"}.map{it.trader.name}.toSet().sortedBy{it}}")
    println("Answer 4 : ${transactions.map{it.trader.name}.sortedBy{it}.toSet()}")
    println("Answer 5 : ${"Milan" in transactions.map{it.trader.city}}")
    println("Answer 6 : ${transactions.filter{it.trader.city=="Cambridge"}.map{it.value}}")
    println("Answer 7 : ${transactions.map{it.value}.max()}")
    println("Answer 8 : ${transactions.map{it.value}.min()}")

}