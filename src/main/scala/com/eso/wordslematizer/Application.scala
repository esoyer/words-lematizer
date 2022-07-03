package com.eso.wordslematizer

import org.apache.spark.sql.SaveMode.Overwrite
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.rogach.scallop.*

object Application {

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args.toSeq)

    implicit val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("words-lemmatizer")
      .getOrCreate()

    import spark.implicits.*

    val wordsDf = spark
      .read
      .csv(conf.wordsPath())
      .as[String]

    LemmatizationJob(conf.dictPath(), conf.affixPath()).run(wordsDf)
      .coalesce(1)
      .write
      .mode(Overwrite)
      .csv(conf.outputPath())
  }
}

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val wordsPath: ScallopOption[String] = opt[String](required = true)
  val dictPath: ScallopOption[String] = opt[String](required = true)
  val affixPath: ScallopOption[String] = opt[String](required = true)
  val outputPath: ScallopOption[String] = opt[String](required = true)
  verify()
}
