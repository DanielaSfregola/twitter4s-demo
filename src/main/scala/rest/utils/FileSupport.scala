package rest.utils

import java.io.PrintWriter

import com.danielasfregola.twitter4s.http.serializers.JsonSupport
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization

import scala.io.Source

trait FileSupport extends JsonSupport {

  private def toJson[T <: AnyRef](value: T): String = pretty(render(parse(Serialization.write(value))))
  private def jsonAs[T: Manifest](json: String): T  = Serialization.read[T](json)

  private def writeToFile(filename: String, content: String) = new PrintWriter(filename) { write(content); close }
  private def readFromFile(filename: String): String         = Source.fromFile(filename).mkString

  def toFileAsJson[T <: AnyRef](filename: String, t: T) = writeToFile(filename, toJson(t))

  def fromJsonFileAs[T: Manifest](filename: String): T = jsonAs(readFromFile(filename))
}
