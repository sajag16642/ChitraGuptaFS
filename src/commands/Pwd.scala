package com.sajag16642
package commands
import filesystem.State

/**
 * @author sajag16642
 */
class Pwd extends Command {
  override def apply(state: State): State =
    state.setMessage(state.wd.path)
}
