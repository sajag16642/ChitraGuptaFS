package com.sajag16642
package commands

import filesystem.State

/**
 * @author sajag16642
 */
class Default extends Command {
  override def apply(state: State): State =
    state.setMessage("Command not found!!")
}
