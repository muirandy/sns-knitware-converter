package sns.lando.knitware.converter

import org.json4s.native.Serialization
import org.json4s.native.Serialization.read
import org.json4s.{Formats, NoTypeHints}

class KnitwareConverter {

  def getXmlFor(textLine: String): String = {
    println(s"input: $textLine")
    implicit val formats: Formats = Serialization.formats (NoTypeHints)
    val enrichedInstruction = read[EnrichedInstruction] (textLine)

    s"""|<?xml version="1.0" encoding="UTF-8"?>
      |<switchServiceModificationInstruction switchServiceId="${enrichedInstruction.SWITCH_SERVICE_ID}" netstreamCorrelationId="${enrichedInstruction.ORDER_ID}">
      |  <features>
      |${featuresToXml(enrichedInstruction.FEATURES)}
      |  </features>
      |</switchServiceModificationInstruction>
    """.stripMargin
  }

  def featuresToXml(features: Seq[String]): String = {
    features.map(f => s"""    <${f.head.toString.toLowerCase + f.tail} active="true"/>""").mkString("\n")
  }
}
