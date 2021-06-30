package com.sajag16642
package commands

import files.{DirEntry, Directory}
import filesystem.State

/**
 * @author sajag16642
 */
abstract class CreateEntry(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) state.setMessage("Entry " + name + " already exists!!")
    else if (isIllegal(name)) state.setMessage(name + " should contains alphabets and numbers only!!")
    else make(state, name)
  }

  def isIllegal(str: String): Boolean = {
    !str.matches("^[a-zA-Z0-9]*$")
  }

  def make(state: State, str: String): State = {

    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
    }

    val wd = state.wd
    val allDirsInPath = wd.getAllFoldersInPath
    val newEntry: DirEntry = createEntry(state, name)
//    val newDir = Directory.empty(fullPath, name)
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    val newWd = newRoot.findDescendant(allDirsInPath)
    State(newRoot, newWd)
  }

  def createEntry(state: State, entryName: String): DirEntry
}
