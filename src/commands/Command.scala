package com.sajag16642
package commands

import filesystem.State

/**
 * @author sajag16642
 */
trait Command {
  def apply(state: State): State
}

object Command{
  def from(input: String): Command =
    new Default
}
