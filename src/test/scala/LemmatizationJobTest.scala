import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest.funsuite.AnyFunSuite

class LemmatizationJobTest extends AnyFunSuite {

  implicit val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("unit tests")
    .getOrCreate()

  import spark.implicits._

  val dictPath = "src/test/resources/fr-classique.dic"
  val affixPath = "src/test/resources/fr-classique.aff"

  test("Should construct dataset of lemmas given a collection of words") {
    // Given
    val wordsDf = spark
      .read
      .format("csv")
      .load("src/test/resources/words.csv")
      .as[String]

    val expectedDf = spark.createDataset(Seq("auteur", "livre+livrer", "lecture"))

    // When
    val result = LemmatizationJob(dictPath, affixPath).run(wordsDf)

    // Then
    assert(areDfEqual(expectedDf, result))
  }

  private def areDfEqual(expected: Dataset[String], actual: Dataset[String]): Boolean = {
    val expectedCount = expected.count()
    val actualCount = actual.count()
    expectedCount == actualCount && expected.intersect(actual).count() == expectedCount
  }
}
