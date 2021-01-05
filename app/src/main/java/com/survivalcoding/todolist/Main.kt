package com.survivalcoding.todolist


data class Trader(val name: String, val city: String)
data class Transaction(val trader: Trader, val year: Int, val value: Int)


val transactions = listOf(
    Transaction(Trader("Brian", "Cambridge"), 2011, 300),
    Transaction(Trader("Raoul", "Cambridge"), 2012, 1000),
    Transaction(Trader("Raoul", "Cambridge"), 2011, 400),
    Transaction(Trader("Mario", "Milan"), 2012, 710),
    Transaction(Trader("Mario", "Milan"), 2012, 700),
    Transaction(Trader("Alan", "Cambridge"), 2012, 950),
)

fun prob1() {
    transactions.filter { it.year == 2011 }
        .sortedBy { it.value }
        .forEach { println(it) }
}

fun prob2() {
    transactions.map { it.trader.city }
        .distinct()
        .forEach { println(it) }
}

fun prob3() {
    transactions.filter { it.trader.city == "Cambridge" }
        .map { it.trader.name }
        .distinct()
        .sortedBy { it }
        .forEach { println(it) }
}

fun prob4() {
    transactions.map { it.trader.name }
        .distinct()
        .sortedBy { it }
        .forEach { println(it) }
}

fun prob5() {
    println(transactions.any { it.trader.city == "Milan" })
}

fun prob6() {
    transactions.filter { it.trader.city == "Cambridge" }
        .forEach { println(it.value) }
}

fun prob7() {
    print(transactions.maxByOrNull { it.value }?.value ?: "값이 없습니다.")
}

fun prob8() {
    print(transactions.minByOrNull { it.value }?.value ?: "값이 없습니다.")
}

fun main() {
}