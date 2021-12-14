package board

class InMemorySquareBoard(override val width: Int) : SquareBoard {

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return when {
            i in (1..width) && j in (1..width) -> Cell(i, j)
            else -> null
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        val cell = getCellOrNull(i, j)
        requireNotNull(cell) { "Could not find Cell(${i}${j})" }
        return cell
    }

    override fun getAllCells(): Collection<Cell> =
        (1..width).asSequence()
            .flatMap { i -> row(i, width) }
            .toList()

    private fun row(rowIndex: Int, columnLength: Int): Sequence<Cell> {
        return (1..columnLength).asSequence().map { j -> Cell(rowIndex, j) }
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.asSequence()
            .map { column -> getCellOrNull(i, column) }
            .filterNotNull()
            .toList()

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.asSequence()
            .map { row -> getCellOrNull(row, j) }
            .filterNotNull()
            .toList()
}