package models.entities

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.libs.json.JsonNaming.SnakeCase

case class OtarProject(otarCode: String,
                       status: String,
                       projectName: String,
                       reference: String,
                       integratesInPPP: Option[Boolean] = Some(false)
)

case class OtarProjects(efoId: String, rows: Seq[OtarProject])

object OtarProjects {
  implicit val config: JsonConfiguration.Aux[Json.MacroOptions] = JsonConfiguration(SnakeCase)
  implicit val otarProjectImpW: OWrites[OtarProject] = Json.writes[OtarProject]

  implicit val otarProjectImpR: Reads[OtarProject] = Json.reads[OtarProject]

  implicit val otarProjectsImpW: OWrites[OtarProjects] = Json.writes[OtarProjects]
  implicit val otarProjectsImpR: Reads[OtarProjects] =
    (
      (__ \ "efo_id").read[String] and
        (__ \ "projects").readWithDefault[Seq[OtarProject]](Seq.empty)
    )(OtarProjects.apply _)
}
