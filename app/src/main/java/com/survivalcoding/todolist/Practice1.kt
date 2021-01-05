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

fun main() {

    // 1
    println("question 1\n=======================\n")
    transactions
        .filter { it.year == 2011 }
        .sortedBy { it.value }
        .forEach { println(it) }

    // 2
    println("\n\nquestion 2\n=======================\n")
    transactions
        .distinctBy { it.trader.city }
        .forEach { print("${it.trader.city} ") }

    // 3
    println("\n\nquestion 3\n=======================\n")
    transactions
        .filter { it.trader.city == "Cambridge" }
        .distinctBy { it.trader.name }
        .sortedBy { it.trader.name }
        .forEach { print("${it.trader.name} ") }

    // 4
    println("\n\nquestion 4\n=======================\n")
    transactions
        .distinctBy { it.trader.name }
        .sortedBy { it.trader.name }
        .forEach { print("${it.trader.name} ") }

    // 5
    println("\n\nquestion 5\n=======================\n")
    transactions
        .filter { it.trader.city == "Milan" }
        .let { cities ->
            if (cities.isEmpty()) println("NO")
            else println("YES")
        }

    // 6
    println("\n\nquestion 6\n=======================\n")
    transactions
        .filter { it.trader.city == "Cambridge" }
        .forEach { print("${it.value} ") }

    // 7
    println("\n\nquestion 7\n=======================\n")
    val values = transactions.map { it.value }.sorted()
//    values.max()

    println(values.last())

    // 8
    println("\n\nquestion 8\n=======================\n")
    println(values.first())
}