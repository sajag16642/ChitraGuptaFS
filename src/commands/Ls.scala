package com.sajag16642
package commands
import filesystem.State

import com.sajag16642.files.DirEntry

/**
 * @author sajag16642
 */
class Ls extends Command {
  override def apply(state: State): State = {
    val contents = state.wd.contents
    val output = create(contents)
    state.setMessage(output)
  }

  def create(contents: List[DirEntry]): String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name+"["+entry.getType+"]\n"+create(contents.tail)
    }
  }
}
