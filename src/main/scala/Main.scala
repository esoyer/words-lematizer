import org.apache.spark.sql.SparkSession
import org.rogach.scallop._

object Main {

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args.toSeq)

    implicit val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("words-analyzer")
      .getOrCreate()

    import spark.implicits._

    val wordsDf = spark
      .read
      .format("csv")
      .load(conf.wordsPath())
      .as[String]

    new LemmatizationJob(conf.dictPath(), conf.affixPath())
      .run(wordsDf)
      .show(false)
  }
}

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val wordsPath: ScallopOption[String] = opt[String](required = true)
  val dictPath: ScallopOption[String] = opt[String](required = true)
  val affixPath: ScallopOption[String] = opt[String](required = true)
  verify()
}
