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
    val queries = listOf(::query1, ::query2, ::query3, ::query4, ::query5, ::query6, ::query7, ::query8)

    queries.forEach {
        it()
        println()
    }
}

fun query1() {
    println("쿼리 1 : 2011년에 일어난 모든 트랜잭션을 찾아 값을 value 기준으로 오름차순으로 정리하시오.")
    transactions
            .filter { it.year == 2011 }
            .sortedBy { it.value }
            .forEach { println(it) }
}

// Map 은 다른 데이터 값으로 가공해서 쓰는 것이 필요하지 않은 이상... 불필요한 while 문을 생성하는 것 같다.
fun query2() {
    println("쿼리 2 : 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.")
    transactions
            .distinctBy { it.trader.city }
            .forEach { println(it.trader.city) }
}

fun query3() {
    println("쿼리 3 : 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.")
    transactions
            .filter { it.trader.city == "Cambridge" }
            .distinctBy { it.trader.name }
            .sortedBy { it.trader.name }
            .forEach { println(it.trader.name) }
}

fun query4() {
    println("쿼리 4 : 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.")
    transactions
            .distinctBy { it.trader.name }
            .sortedBy { it.trader.name }
            .forEach { println(it.trader.name) }
}

fun query5() {
    println("쿼리 5 : 밀라노에 거래자가 있는가?")
    println(transactions.any { it.trader.city == "Milan" })
}

fun query6() {
    println("쿼리 6 : 케임브리지에 근무하는 거래자의 모든 트랙잭션값을 출력하시오.")
    transactions
            .filter { it.trader.city == "Cambridge" }
            .forEach { println(it) }
}

fun query7() {
    println("쿼리 7 : 전체 트랜잭션 중 최댓값을 얼마인가?")
    println(transactions.maxBy { it.value }?.value)
}

fun query8() {
    println("쿼리 8 : 전체 트랜잭션 중 최솟값은 얼마인가?")
    println(transactions.minBy { it.value }?.value)
}