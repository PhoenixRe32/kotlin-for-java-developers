package board

class GameBoardImpl<T>(
    override val width: Int,
    private val squareBoard: SquareBoard = ArraySquareBoard(width),
    private val cellMap: MutableMap<Cell, T?> = squareBoard.getAllCells().associateWith { null }.toMutableMap()
) : GameBoard<T> {

    override fun getCellOrNull(i: Int, j: Int): Cell? = squareBoard.getCellOrNull(i, j)

    override fun getCell(i: Int, j: Int): Cell = squareBoard.getCell(i, j)

    override fun getAllCells(): Collection<Cell> = squareBoard.getAllCells()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = squareBoard.getRow(i, jRange)

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = squareBoard.getColumn(iRange, j)

    override fun get(cell: Cell): T? = cellMap[cell]

    override fun set(cell: Cell, value: T?) {
        when {
            value != null -> {
                getCellOrNull(cell.i, cell.j)?.apply {
                    cellMap[this] = value
                }
            }
        }
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellMap.asSequence()
        .filter { (_, value) -> predicate.invoke(value) }
        .map { it.key }
        .toList()


    override fun find(predicate: (T?) -> Boolean): Cell? = cellMap.asSequence()
        .find { (_, value) -> predicate.invoke(value) }
        ?.key

    override fun any(predicate: (T?) -> Boolean): Boolean = cellMap.asSequence()
        .any { (_, value) -> predicate.invoke(value) }

    override fun all(predicate: (T?) -> Boolean): Boolean = cellMap.asSequence()
        .all { (_, value) -> predicate.invoke(value) }
}
