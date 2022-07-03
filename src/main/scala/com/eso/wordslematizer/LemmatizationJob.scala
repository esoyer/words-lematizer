package com.eso.wordslematizer

import org.apache.spark.sql.{Dataset, SparkSession}

case class LemmatizationJob(dictionaryPath: String, affixPath: String) {

  def run(words: Dataset[String])(implicit spark: SparkSession): Dataset[String] = {
    import spark.implicits.*
    val lemmatizer = Lemmatizer(dictionaryPath, affixPath)
    words.map(lemmatizer.toLemmas)
  }
}
