package taxipark

import java.lang.Integer.min

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers
        .asSequence()
        .map { driver -> driver to trips.filter { driver == it.driver } }
        .map { (driver, trips) -> driver to trips.size }
        .filter { (_, trips) -> trips == 0 }
        .map { (driver, _) -> driver }
        .toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers
        .asSequence()
        .map { passenger -> passenger to trips.filter { passenger in it.passengers } }
        .map { (passenger, trips) -> passenger to trips.size }
        .filter { it.second >= minTrips }
        .map { it.first }
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    trips
        .asSequence()
        .filter { it.driver == driver }
        .flatMap { it.passengers.asSequence() }
        .groupBy { it }
        .filter { it.value.size > 1 }
        .map { it.key }
        .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers
        .asSequence()
        .map { passenger -> passenger to trips.filter { passenger in it.passengers } }
        .map { (passenger, trips) ->
            passenger to (trips.count { it.discount != null && it.discount > 0 } / trips.size.toDouble())
        }
        .filter { (_, discountedTripPercentage) -> discountedTripPercentage > 0.5 }
        .map { (passenger, _) -> passenger }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
    trips
        .asSequence()
        .map { it.duration }
        .groupBy { (it / 10) * 10..((it / 10) * 10) + 9 }
        .maxBy { it.value.size }
        ?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty())
        return false

    val income = trips
        .groupBy(Trip::driver, Trip::cost)
        .map { it.value.sum() }
        .sortedByDescending { it }
    val totalIncome = income.sum()
    val fifthIncome = income.take(min(allDrivers.size / 5, income.size)).sum()
    return fifthIncome >= totalIncome * 0.8
}