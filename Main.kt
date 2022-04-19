//package parking

class parkingSpot (var theStatus: String, var theCar: String, var theColor: String, var theIndex: Int)

fun main() {
    var checkpoint = false
    var theInput :List<String>
    var theCommand = ""
    var theSpotIndex = 0
    var spaces = 0
    var parkingLotExist = false
    var countSpaces = 0
    var theSpots = MutableList(spaces) { parkingSpot("empty","empty","empty", 0) }
    while (!checkpoint) {
        print("> ")
        theInput = readln().split(" ").map { it.toString() }
        theCommand = theInput[0]
        when (theCommand) {
            "park" -> {
                if (parkingLotExist) {
                    theSpotIndex = checkFreeFirstSpot(theSpots)
                    fillSpot(theSpots, theSpotIndex, theInput)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "leave" -> {
                if (parkingLotExist) {
                    checkFreeTheSpot(theSpots, theInput)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "status" -> {
                if (parkingLotExist) {
                    countSpaces = 0
                    for (a in 0 until theSpots.size) {
                        if (theSpots[a].theStatus == "empty") countSpaces++
                    }
                    if (countSpaces == spaces) {
                        println("Parking lot is empty.")
                    } else {
                        for (i in 0 until theSpots.size) {
                            if (theSpots[i].theStatus != "empty") println("${i + 1} ${theSpots[i].theCar} ${theSpots[i].theColor}")
                        }
                    }
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "create" -> {
                if (parkingLotExist) {
                    var (tempSpaces, tempSpots, tempExistence)= createParkingLot(theInput)
                    spaces = tempSpaces
                    theSpots = tempSpots
                    parkingLotExist = tempExistence
                } else {
                    var (tempSpaces, tempSpots, tempExistence)= createParkingLot(theInput)
                    spaces = tempSpaces
                    theSpots = tempSpots
                    parkingLotExist = tempExistence
                }
            }
            "spot_by_color" -> {
                if (parkingLotExist) {
                    searchCar(theInput,theSpots)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "spot_by_reg" -> {
                if (parkingLotExist) {
                    searchCar(theInput,theSpots)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "reg_by_color" -> {
                if (parkingLotExist) {
                    searchCar(theInput,theSpots)
                } else {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            "exit" -> checkpoint = true
        }
    }
}
fun createParkingLot(theInput: List<String>): Triple<Int, MutableList<parkingSpot>, Boolean> {
    var theParkingLot = MutableList(theInput[1].toInt()) {parkingSpot("empty","empty","empty", 0)}
    for (a in 0 until theInput[1].toInt()) theParkingLot[a].theIndex = a + 1
    println("Created a parking lot with ${theInput[1]} spots.")
    return Triple(theInput[1].toInt(),theParkingLot,true)
}

fun checkFreeFirstSpot(theSpots: MutableList<parkingSpot>): Int {
    var theFreeIndex = 0
    for (a in theSpots.indices) {
        if (theSpots[a].theStatus == "empty") {
            theFreeIndex = a
            break
        } else theFreeIndex = 999
    }
    return theFreeIndex
}
fun fillSpot (theSpots: MutableList<parkingSpot>, theSpotIndex: Int, theInput: List<String>):Boolean {
    var taskCompleted = false
    if (theSpotIndex != 999) {
        theSpots[theSpotIndex].theStatus = "full"
        theSpots[theSpotIndex].theCar = theInput[1]
        theSpots[theSpotIndex].theColor = theInput[2]
        println("${theInput[2]} car parked in spot ${theSpotIndex + 1}.")
        taskCompleted = true
    } else {
        println("Sorry, the parking lot is full.")
        taskCompleted = true
    }
    return taskCompleted
}

fun checkFreeTheSpot(theSpots: MutableList<parkingSpot>, theInput: List<String>): Boolean {
    var theFreeIndex = 0
    var a = theInput[1].toInt()
    a--
    var taskCompleted = false
    if (theSpots[a].theStatus == "empty") {
        theFreeIndex = a
        println("There is no car in spot ${theFreeIndex + 1}.")
        taskCompleted = true
    }
    if (theSpots[a].theStatus == "full") {
        if (freeSpot(theSpots[a])) {
            println("Spot ${a + 1} is free.")
            taskCompleted = true
        }
    }
    return taskCompleted
}

fun freeSpot (theFullSpot: parkingSpot): Boolean {
    var taskCompleted = false
    theFullSpot.theStatus = "empty"
    theFullSpot.theCar = "empty"
    theFullSpot.theColor = "empty"
    taskCompleted = true
    return taskCompleted
}
fun searchCar(theInput: List<String>, theSpots: MutableList<parkingSpot>): Boolean {
    //var theColors = mutableListOf<String>("red","brown","green","blue","black","yellow","white","orange","violet","pink","grey","aqua")
    var theCommand = theInput[0]
    var searchedColor = theInput[1].lowercase()
    var registrationNumber = theInput[1]
    var resultList = mutableListOf<String>()
    var taskCompleted = false
    when (theCommand) {
        "reg_by_color" -> {
            var found = 0
            for (a in 0 until theSpots.size) {
                if (theSpots[a].theColor.lowercase() == searchedColor) {
                    found++
                    resultList.add(theSpots[a].theCar)
                }
            }
            if (found > 0) println(resultList.joinToString()) else println("No cars with color ${searchedColor.uppercase()} were found.")
        }
        "spot_by_color" -> {
            var found = 0
            for (a in 0 until theSpots.size) {
                if (theSpots[a].theColor.lowercase() == searchedColor) {
                    found++
                    resultList.add(theSpots[a].theIndex.toString())
                }
            }
            if (found > 0) println(resultList.joinToString()) else println("No cars with color ${searchedColor.uppercase()} were found.")
        }
        "spot_by_reg" -> {
            var found = 0
            for (a in 0 until theSpots.size) {
                if (theSpots[a].theCar == registrationNumber) {
                    found++
                    resultList.add(theSpots[a].theIndex.toString())
                }
            }
            if (found > 0) println(resultList.joinToString()) else println("No cars with registration number ${registrationNumber} were found.")
        }
    }
    return taskCompleted
}
