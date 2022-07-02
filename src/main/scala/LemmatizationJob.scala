import com.atlascopco.hunspell.Hunspell
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.jdk.CollectionConverters._
import scala.util.Using

case class LemmatizationJob(dictionaryPath: String, affixPath: String) {

  def run(words: Dataset[String])(implicit spark: SparkSession): Dataset[String] = {
    import spark.implicits._
    words.map(LemmatizationJob.toLemmas(_, dictionaryPath, affixPath))
  }
}

object LemmatizationJob {

  def toLemmas(word: String, dictionaryPath: String, affixPath: String): String = {
    Using(new Hunspell(dictionaryPath, affixPath)) { hunspell =>
      hunspell.analyze(word).asScala
        .map(extractStem)
        .distinct
        .mkString("+")
    }.get
  }

  private def extractStem(s: String) = {
    s.trim.split(' ')(0).replace("st:", "")
  }
}
