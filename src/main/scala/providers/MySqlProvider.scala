package providers
import slick.jdbc.MySQLProfile

trait MySqlProvider extends DBProvider{
  val driver = MySQLProfile

  import driver.api._

  val db = Database.forConfig("mySqlDB")

}
