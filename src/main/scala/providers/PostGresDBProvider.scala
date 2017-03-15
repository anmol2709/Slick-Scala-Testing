package providers
import slick.jdbc.PostgresProfile

trait PostGresDBProvider extends DBProvider{
  val driver = PostgresProfile

  import driver.api._

  val db = Database.forConfig("myPostgresDB")

}
