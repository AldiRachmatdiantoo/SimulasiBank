sealed class OutputsOperation {
    data class Success(val msg: String): OutputsOperation()
    data class Error(val msg: String): OutputsOperation()
}