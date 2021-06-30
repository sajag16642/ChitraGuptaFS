package com.sajag16642
package commands
import files.{DirEntry, File}
import filesystem.State

/**
 * @author sajag16642
 */
class Touch(name: String) extends CreateEntry(name) {
  override def createEntry(state: State, entryName: String): DirEntry =
    File.empty(state.wd.path,entryName)
}
