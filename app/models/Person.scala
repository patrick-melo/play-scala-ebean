package models

import com.avaje.ebean.Model
import javax.persistence._
import play.db.ebean._
import play.data.validation.Constraints._
import scala.collection.JavaConverters._
import play.api.libs.json.Json
import play.api.libs.json._

@Entity
case class Person() extends Model {
  @Id
  var id: Long = 0
  var name:String = ""
}

object Person {
  var find: Model.Finder[Long, Person] = new Model.Finder[Long, Person](classOf[Long], classOf[Person])

  def all(): List[Person] = find.all.asScala.toList

  def create(name: String) {
    val person =  new Person()
    person.save
  }

  def delete(id: Long) {
    find.ref(id).delete
  }

  implicit object PersonFormat extends Format[Person] {
    // convert from Person object to JSON (serializing to JSON)
    def writes(person: Person): JsValue = {
      val personSeq = Seq(
        "id" -> JsString(""+person.id),
        "name" -> JsString(person.name)
      )
      JsObject(personSeq)
    }
    // convert from JSON string to a Person object (de-serializing from JSON)
    def reads(json: JsValue): JsResult[Person] = {
      JsSuccess(Person())
    }
  }

}

case class PersonData(name: String)