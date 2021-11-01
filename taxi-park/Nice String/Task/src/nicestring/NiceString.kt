package nicestring

fun String.isNice(): Boolean {
    return listOf(
        setOf("bu", "ba", "be").none { it in this },
        count { it in setOf('a', 'e', 'i', 'o', 'u') } >= 3,
        zipWithNext().stream().anyMatch { it.first == it.second }
    ).count { it == true } >= 2;
}