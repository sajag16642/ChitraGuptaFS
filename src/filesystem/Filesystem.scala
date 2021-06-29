package com.sajag16642
package filesystem

import com.sajag16642.commands.Command
import com.sajag16642.files.Directory

import java.util.Scanner

/**
 * @author sajag16642
 */
object Filesystem extends App{

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while(true){
    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
