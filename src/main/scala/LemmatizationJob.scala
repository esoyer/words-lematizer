import com.atlascopco.hunspell.Hunspell
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.jdk.CollectionConverters._
import scala.util.Using

case class LemmatizationJob(dictionaryPath: String, affixPath: String) {

  def run(words: Dataset[String])(implicit spark: SparkSession): Dataset[String] = {
    import spark.implicits._
    val lemmatizer = Lemmatizer(dictionaryPath, affixPath)
    words.map(lemmatizer.toLemmas)
  }
}