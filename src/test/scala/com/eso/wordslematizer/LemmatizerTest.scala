package com.eso.wordslematizer

import com.eso.wordslematizer.Lemmatizer
import org.scalatest.funsuite.AnyFunSuite

class LemmatizerTest extends AnyFunSuite {

  val dictPath = "src/test/resources/fr-classique.dic"
  val affixPath = "src/test/resources/fr-classique.aff"

  test("Should livre have two lemmas : livre and livrer") {
    assertResult("livre+livrer")(Lemmatizer(dictPath, affixPath).toLemmas("livre"))
  }
}
