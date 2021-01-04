package com.survivalcoding.todolist


// 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 value 기준으로 오름차순으로 정리하시오
// 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오
// 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오
// 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오
// 5. 밀라노에 거래자가 있는가?
// 6. 케임브리지에 근무하는 거래자의 모든 트랙잭션값을 출력하시오
// 7. 전체 트랜잭션 중 최댓값을 얼마인가?
// 8. 전체 트랜잭션 중 최솟값은 얼마인가?

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
    problem1()
    problem2()
    problem3()
    val problem4Answer = problem4()
    problem4Answer.forEach { println(it) }
    println("------------------------------------------------")
    problem5()
    problem6()
    problem7()
    problem8()
}

fun problem1() {
    transactions.filter { it.year == 2011 } // 2011
        .sortedBy { it.value } // ascending sort by value
        .forEach{ println(it)}
    println("------------------------------------------------")
}

fun problem2(){
    val problem2Result = mutableListOf<String>()
     transactions.distinctBy { it.trader.city } // remove overlap
        .forEach{problem2Result.add(it.trader.city)} // add List
    problem2Result.forEach{ println(it)}
    println("------------------------------------------------")
}

fun problem3(){
    val problem3Result = mutableListOf<Trader>()
    transactions.filter { it.trader.city.equals("Cambridge") } // "Cambridge"
        .sortedBy { it.trader.name }
        .forEach{problem3Result.add(it.trader)}
    problem3Result.forEach { println(it) }
    println("------------------------------------------------")
}

fun problem4() : List<String>{
    val problem4Result = mutableListOf<String>()
    transactions.distinctBy { it.trader.name } // remove overlap
        .sortedBy { it.trader.name } // sort
        .forEach{problem4Result.add(it.trader.name)}
    return problem4Result
}

fun problem5(){
    val problem5Result = transactions.filter { it.trader.city.equals("Milan") }
    if(problem5Result.isEmpty()) println("No, There is not Trader in Milan") else println("Yes, There is Trader in Milan") // empty -> no
    println("------------------------------------------------")
}

fun problem6(){
    transactions.filter { it.trader.city.equals("Cambridge") }
        .zip(transactions.filter { it.trader.city.equals("Cambridge") }).forEach{ println("${it.first.trader.name}: ${it.second.value}")} // (name -> value)
    println("------------------------------------------------")
}

fun problem7(){
    val problem7Result = transactions.maxByOrNull{it.value}
    println(if (problem7Result != null) problem7Result.value else "Not Max Value") // max
    println("------------------------------------------------")
}

fun problem8(){
    val problem8Result = transactions.minByOrNull{it.value}
    println(if (problem8Result != null) problem8Result.value else "Not Min Value") // min
    println("------------------------------------------------")
}