package com.sajag16642
package commands

import filesystem.State

/**
 * @author sajag16642
 */
trait Command {
  def apply(state: State): State
}

object Command {
  val MKDIR = "mkdir"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State =
      state.setMessage(name+" :too few arguments!!")
  }

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else {
      if (MKDIR.equals(tokens(0))) {
        if (tokens.length < 2) incompleteCommand(MKDIR)
        else new Mkdir(tokens(1))
      }
      else new Default
    }
  }
}
