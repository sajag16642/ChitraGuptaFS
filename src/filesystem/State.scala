package com.sajag16642
package filesystem

import files.Directory

import jdk.javadoc.internal.doclets.toolkit.util.DocFinder.Output

/**
 * @author sajag16642
 */
class State(val root: Directory, val wd: Directory, val output: String) {
  def show: Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State =
    State(root, wd, message)
}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State =
    new State(root, wd, output)
}
