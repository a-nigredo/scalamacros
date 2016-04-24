// NOTE: commented out in order to avoid scope pollution for typecheckError
// package scala.meta.tests
// package api

import org.scalatest._
import org.scalameta.tests._

class PublicSuite extends FunSuite {
  test("quasiquotes without import") {
    assert(typecheckError("""
      q"hello"
    """) === "value q is not a member of StringContext")
  }

  test("quasiquotes without static dialect") {
    assert(typecheckError("""
      import scala.meta._
      implicit val dialect: scala.meta.Dialect = ???
      q"hello"
    """) === "dialect does not have precise enough type to be used in quasiquotes (to fix this, import something from scala.dialects, e.g. scala.meta.dialects.Scala211)")
  }

  test("quasiquotes when everything's correct") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      q"hello"
    """) === "")
  }

  test("InputLike.parse without import") {
    assert(typecheckError("""
      "".parse[scala.meta.Term]
    """) === "value parse is not a member of String")
  }

  test("InputLike.parse without input-likeness") {
    assert(typecheckError("""
      import scala.meta._
      1.parse[Term]
    """) === "don't know how to convert Int to scala.meta.inputs.Input")
  }

  test("InputLike.parse without parseability") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      "".parse[Int]
    """) === "don't know how to parse into Int")
  }

  test("InputLike.parse when everything's correct (static dialect)") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      "".parse[Term]
    """) === "")
  }

  test("InputLike.parse when everything's correct (dynamic dialect)") {
    assert(typecheckError("""
      import scala.meta._
      implicit val dialect: scala.meta.Dialect = ???
      "".parse[Term]
    """) === "")
  }

  test("InputLike.parse with various input types") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      (??? : Input).parse[Term]
      (??? : String).parse[Term]
      (??? : java.io.File).parse[Term]
      (??? : Tokens).parse[Term]
      (??? : Array[Char]).parse[Term]
    """) === "")
  }

  // NOTE: this works because implicit scope for Scala211 includes meta.`package`
  test("Dialect.parse without import") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211("").parse[scala.meta.Term]
    """) === "")
  }

  test("Dialect.parse without input-likeness") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211(1).parse[scala.meta.Term]
    """) === "don't know how to convert Int to scala.meta.inputs.Input")
  }

  test("Dialect.parse without parseability") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211("").parse[Int]
    """) === "don't know how to parse into Int")
  }

  test("Dialect.parse with various input types") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211(??? : scala.meta.Input).parse[scala.meta.Term]
      scala.meta.dialects.Scala211(??? : String).parse[scala.meta.Term]
      scala.meta.dialects.Scala211(??? : java.io.File).parse[scala.meta.Term]
      scala.meta.dialects.Scala211(??? : scala.meta.Tokens).parse[scala.meta.Term]
      scala.meta.dialects.Scala211(??? : Array[Char]).parse[scala.meta.Term]
    """) === "")
  }

  test("tokenize without import") {
    assert(typecheckError("""
      "".tokenize
    """) === "value tokenize is not a member of String")
  }

  test("tokenize without input-likeness") {
    assert(typecheckError("""
      import scala.meta._
      1.tokenize
    """) === "don't know how to convert Int to scala.meta.inputs.Input")
  }

  test("tokenize when everything's correct (static dialect)") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      "".tokenize
    """) === "")
  }

  test("tokenize when everything's correct (dynamic dialect)") {
    assert(typecheckError("""
      import scala.meta._
      implicit val dialect: scala.meta.Dialect = ???
      "".tokenize
    """) === "")
  }

  test("tokenize with various input types") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      (??? : Input).tokenize
      (??? : String).tokenize
      (??? : java.io.File).tokenize
      (??? : Tokens).tokenize
      (??? : Array[Char]).tokenize
    """) === "")
  }

  // NOTE: this works because implicit scope for Scala211 includes meta.`package`
  test("Dialect.tokenize without import") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211("").tokenize
    """) === "")
  }

  test("Dialect.tokenize without input-likeness") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211(1).tokenize
    """) === "don't know how to convert Int to scala.meta.inputs.Input")
  }

  test("Dialect.tokenize when everything's correct") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211("").tokenize
    """) === "")
  }

  test("Dialect.tokenize with various input types") {
    assert(typecheckError("""
      scala.meta.dialects.Scala211(??? : scala.meta.Input).tokenize
      scala.meta.dialects.Scala211(??? : String).tokenize
      scala.meta.dialects.Scala211(??? : java.io.File).tokenize
      scala.meta.dialects.Scala211(??? : scala.meta.Tokens).tokenize
      scala.meta.dialects.Scala211(??? : Array[Char]).tokenize
    """) === "")
  }

  test("show[Code] without import") {
    assert(typecheckError("""
      (??? : scala.meta.Tree).show[Code]
    """) === "not found: type Code")
  }

  test("show[Code] when everything's correct (static dialect)") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      (??? : Tree).show[Code]
    """) === "")
  }

  test("show[Code] when everything's correct (dynamic dialect)") {
    assert(typecheckError("""
      import scala.meta._
      implicit val dialect: scala.meta.Dialect = ???
      (??? : Tree).show[Code]
    """) === "")
  }

  test("show[Syntax] without import") {
    assert(typecheckError("""
      (??? : scala.meta.Tree).show[Syntax]
    """) === "not found: type Syntax")
  }

  test("show[Syntax] when everything's correct (static dialect)") {
    assert(typecheckError("""
      import scala.meta._
      import scala.meta.dialects.Scala211
      (??? : Tree).show[Syntax]
    """) === "")
  }

  test("show[Syntax] when everything's correct (dynamic dialect)") {
    assert(typecheckError("""
      import scala.meta._
      implicit val dialect: scala.meta.Dialect = ???
      (??? : Tree).show[Syntax]
    """) === "")
  }

  test("show[Raw] without import") {
    assert(typecheckError("""
      (??? : scala.meta.Tree).show[Raw]
    """) === "not found: type Raw")
  }

  test("show[Raw] when everything's correct") {
    assert(typecheckError("""
      import scala.meta._
      (??? : Tree).show[Raw]
    """) === "")
  }

  test("show[Structure] without import") {
    assert(typecheckError("""
      (??? : scala.meta.Tree).show[Structure]
    """) === "not found: type Structure")
  }

  test("show[Structure] when everything's correct") {
    assert(typecheckError("""
      import scala.meta._
      (??? : Tree).show[Structure]
    """) === "")
  }
}
