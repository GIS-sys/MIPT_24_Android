fun fibMod(n: Long, m: Long): Long {
    val MAXMOD: Long = 1000000
    var cache: List<Long> = listOf(0 + 1 * MAXMOD)
    var cacheSet: HashSet<Long> = hashSetOf(0 + 1 * MAXMOD)
    var fibPair: Long = 1 + 1 * MAXMOD
    while (!cacheSet.contains(fibPair)) {
        var fib1: Long = fibPair % MAXMOD
        var fib2: Long = fibPair / MAXMOD
        cache += fibPair
        cacheSet += fibPair
        fibPair = fib2 + ((fib1 + fib2) % m) * MAXMOD
    }
    var cycleStart = cache.indexOf(fibPair)
    var cycleSize = cache.size - cycleStart;
    var index = (cycleStart + (n - cycleStart) % cycleSize).toInt()
    return cache[index] % MAXMOD
}

fun main(args: Array<String>) {
    if (fibMod(10, 2) != 1L) return;
    if (fibMod(10, 3) != 1L) return;
    if (fibMod(10, 4) != 3L) return;
    var (n, m) = readln().split(' ')
    println(fibMod(n.toLong(), m.toLong()))
}

