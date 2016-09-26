package rest.utils

import java.io.PrintWriter
import java.text.SimpleDateFormat

import com.danielasfregola.twitter4s.http.unmarshalling.CustomSerializers
import org.json4s.{DefaultFormats, Formats}
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import spray.httpx.Json4sSupport

import scala.io.Source

trait FileSupport extends Json4sSupport {

  implicit def json4sFormats: Formats = defaultFormats ++ CustomSerializers.all

  val defaultFormats = new DefaultFormats {
    override def dateFormatter = {
      val simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZ yyyy")
      simpleDateFormat
    }
  }

  private def toJson[T <: AnyRef](value: T): String = pretty(render(parse(Serialization.write(value))))
  private def jsonAs[T: Manifest](json: String): T = Serialization.read[T](json)

  private def writeToFile(filename: String, content: String) = new PrintWriter(filename) { write(content); close }
  private def readFromFile(filename: String): String = Source.fromFile(filename).mkString

  def toFileAsJson[T <: AnyRef](filename: String, t: T) = writeToFile(filename, toJson(t))

  def fromJsonFileAs[T: Manifest](filename: String): T = jsonAs(readFromFile(filename))
}
