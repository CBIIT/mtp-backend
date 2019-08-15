package models

import clickhouse.rep.SeqRep._
import play.api.libs.json.Json
import slick.jdbc.GetResult

import scala.math.pow
import models.entities.Target

object Entities {
  case class MetaVersion(x: Int, y: Int, z: Int)
  case class Meta(name: String, version: MetaVersion)

  case class ElasticsearchSettings(host: String, port: Int)

  case class Pagination(index: Int, size: Int) {
    def toSQL: String = (index, size) match {
      case (0, 0) => s"LIMIT ${Pagination.sizeDefault}"
      case (0, s) => s"LIMIT $s"
      case (i, 0) => s"LIMIT ${i * Pagination.sizeDefault}, ${Pagination.sizeDefault}"
      case (i, s) => s"LIMIT ${i * s} , $s"
      case _ => s"LIMIT ${Pagination.indexDefault}, ${Pagination.sizeDefault}"
    }
  }

  object Pagination {
    val sizeMax: Int = 10000
    val sizeDefault: Int = 100
    val indexDefault: Int = 0
    def mkDefault: Pagination = Pagination(indexDefault, sizeDefault)
  }

  object JSONImplicits {
    implicit val metaVersionImp = Json.format[Entities.MetaVersion]
    implicit val metaImp = Json.format[Entities.Meta]

    implicit val esSettingsImp = Json.reads[Entities.ElasticsearchSettings]

    implicit val targetImp = Json.format[models.entities.Target]
  }
}
