package com.sajag16642
package commands

import filesystem.State

import com.sajag16642.files.Directory

/**
 * @author sajag16642
 */
class Rm(name: String) extends Command {

  def remove(state: State, path: String): State = {
    def removeHelper(currentDirectory: Directory, path: List[String]): Directory = {
      if(path.isEmpty) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if(!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = removeHelper(nextDirectory.asDirectory, path.tail)
          if(newNextDirectory == nextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot = removeHelper(state.root, tokens)

    if(newRoot == state.root)
      state.setMessage("No such file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
  }

  override def apply(state: State): State = {
    val wd = state.wd

    val absolutePath =
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name

    if (Directory.ROOT_PATH.equals(absolutePath)) state.setMessage("Directory Root can't be deleted!!")
    else remove(state, absolutePath)
  }
}
