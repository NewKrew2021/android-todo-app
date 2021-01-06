package com.survivalcoding.todolist.lecture

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

// 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 value 기준으로 오름차순으로 정리하시오
// 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오
// 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오
// 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오
// 5. 밀라노에 거래자가 있는가?
// 6. 케임브리지에 근무하는 거래자의 모든 트랙잭션값을 출력하시오
// 7. 전체 트랜잭션 중 최댓값을 얼마인가?
// 8. 전체 트랜잭션 중 최솟값은 얼마인가?

fun main() {
    JanFifth()

    println("--- 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 value 기준으로 오름차순으로 정리하시오 ---")
    transactions.filter { it.year == 2011 }.sortedBy { it.value }.forEach { println(it) }

    println("\n--- 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오 ---")
    transactions.distinctBy { it.trader.city }.forEach { println(it.trader.city) }
    transactions.map { it.trader.city }.distinct().forEach { println(it) }

    println("--- 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오 ---")
    // == 대신에 equals를 썼는데, 노란줄이 생겼다.
    // 이름만 출력하려면 print("${it.trader.name} ")
    transactions.filter { it.trader.city == "Cambridge" }.sortedBy { it.trader.name }.forEach { println(it) }
    transactions.filter { it.trader.city == "Cambridge" }.distinctBy { it.trader.name }.sortedBy { it.trader.name }.forEach { println(it.trader.name) }
    transactions.filter { it.trader.city == "Cambridge" }.map { it.trader.name }.distinct().sorted().forEach { println(it) }

    println("\n--- 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오 ---")
    // 반환하라고 해서 그냥 변수에 값을 담았다.
    // 혹시나 해서 중복제거도 하였다.
    var result = transactions.map { it.trader.name }.distinct().sorted()
    for (res in result) print("$res ")

    println("\n--- 5. 밀라노에 거래자가 있는가? ---")
    var isYes: Boolean = transactions.any { it.trader.city == "Milan" }
    println("밀라노에 거래자가 있는가 (T/F) : $isYes")


    println("\n--- 6. 케임브리지에 근무하는 거래자의 모든 트랙잭션값을 출력하시오 ---")
    transactions.filter { it.trader.city == "Cambridge" }.forEach { println(it) }

    println("\n--- 7. 전체 트랜잭션 중 최댓값을 얼마인가? ---")  // ....???? 왜 밑줄이..
    val max_value: Int? = transactions.map { it.value }.maxOrNull()
    println("max_value : ${max_value}")

    println("--- 8. 전체 트랜잭션 중 최솟값은 얼마인가? ---")
    val min_value: Int? = transactions.map { it.value }.minOrNull()
    println("min_value : $min_value")
}

fun JanFifth() {
    val hexBytes = 0xFF_EC_DE_5E
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val hexByte2 = 0x7F_FF_FF_FF

    val array = (0..5)  // IntRange
    val otherArray = arrayOf(0, 1, 2, 3, 4, 5)  // Array<Int>

    println("hexByte : $hexBytes")
    println("oneMillion : $oneMillion")
    println("hexByte2 : $hexByte2")

    println("array : $array")
    println("otherArray : $otherArray")

    val data = array.map { it.toString() }.toList() // data : List<String>
    println("data : $data")

    val listInt = listOf<Int>(0, 1, 2, 3, 4, 5)
    val intRangeToList = (0..5).toList()
    println("ListInt == IntRangeToList : ${listInt == intRangeToList}")
}