import com.simulasibank.auth.Login
import java.sql.PreparedStatement
import kotlin.system.exitProcess

fun main() {
    val toProgram = checkYesOrNo("masuk ke aplikasi CLI Bank? ")
    if (!toProgram) return println("Terima kasih!")


    while (true){
        Login().loginAcc()
        println("\nTerima Kasih!")
        break
    }
}
fun checkYesOrNo(text: String): Boolean{
    print(text)
    var choice = readln()
    while (!choice.equals("y", true) && !choice.equals("n", true)){
        println("error! tolong masukkan input dengan benar!")
        print(text)
        choice = readln()
    }
    return choice.equals("y", true)
}
fun checkNullOrBlank(text: String): String{
    while (true) {
        print(text)
        val input = readln()
        if (!input.isBlank()) return input
    }
}
fun checkSqlTable(stmt: PreparedStatement, input: String, getStringFromSql: String): Boolean{
    val rs = stmt.executeQuery()
    if (rs.next()){
        val getRowFromSql = rs.getString(getStringFromSql)
        return getRowFromSql == input
    }
    return false
}
fun checkGetSql(stmt: PreparedStatement, getStringFromSql: String): String{
    val rs = stmt.executeQuery()
    if (rs.next()){
        val get = rs.getString(getStringFromSql)
        return get
    }
    return "-"

}
fun handle(output: OutputsOperation) {
    when(output){
        is OutputsOperation.Success -> println("\nSuccess: ${output.msg}\n")
        is OutputsOperation.Error -> println("\nError: ${output.msg}\n")
    }
}
var chance = 3
fun remainingChance(): Int{
    chance--
    if (chance <= 0) {
        handle(OutputsOperation.Error("kesempatan habis!"))
        exitProcess(0)
    }
    return chance
}