import com.atlascopco.hunspell.Hunspell

import scala.jdk.CollectionConverters.*

case class Lemmatizer(dictionaryPath: String, affixPath: String) {

  // As I cannot prove that the Hunspell class is thread safe, threads will not share a common Hunspell instance to use
  @transient private lazy val hunspell = ThreadLocal.withInitial(() => new Hunspell(dictionaryPath, affixPath))

  def toLemmas(word: String): String = {
    hunspell.get()
      .analyze(word).asScala
      .map(extractStem)
      .distinct
      .mkString("+")
  }

  private def extractStem(s: String) = {
    s.trim.split(' ')(0).replace("st:", "")
  }
}
