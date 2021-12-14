package board

fun createSquareBoard(width: Int): SquareBoard = ArraySquareBoard(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)


