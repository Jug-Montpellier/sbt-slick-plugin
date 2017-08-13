package io.metabookmarks.slickcodegen

import scala.util.Try

trait Compat {
    def toError(tr: Try[Unit]) = tr.recover {
      case e =>
    //logger.error("e.getMessage")
  }
}
