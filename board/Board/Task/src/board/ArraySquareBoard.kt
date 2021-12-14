package board

class ArraySquareBoard(override val width: Int) : SquareBoard {
    private val cells: Array<Array<Cell>> = Array(width)
    { row ->
        Array(width)
        { column -> Cell(row + 1, column + 1) }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        val row = i - 1
        val column = j - 1
        return cells.getOrNull(row)?.getOrNull(column)
    }

    override fun getCell(i: Int, j: Int): Cell {
        val cell = getCellOrNull(i, j)
        requireNotNull(cell) { "Could not find Cell(${i}${j})" }
        return cell
    }

    override fun getAllCells(): Collection<Cell> = cells.flatten()

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