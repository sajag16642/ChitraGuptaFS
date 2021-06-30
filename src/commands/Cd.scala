package com.sajag16642
package commands
import filesystem.State

import com.sajag16642.files.{DirEntry, Directory}

import scala.annotation.tailrec

/**
 * @author sajag16642
 */
class Cd(dir: String) extends Command{



  def findEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    @tailrec
    def collapse(path: List[String], result: List[String]): List[String] = {
      if(path.isEmpty) result
      else if(".".equals(path.head)) collapse(path.tail, result)
      else if("..".equals(path.head)){
        if(result.isEmpty) null
        else collapse(path.tail, result.init)
      }
      else collapse(path.tail, result:+path.head)
    }

    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    val newTokens = collapse(tokens, List())
    findEntryHelper(root, newTokens)
  }

  override def apply(state: State): State = {
    val root = state.root
    val wd = state.wd

    val absolutePath =
      if(dir.startsWith(Directory.SEPARATOR)) dir
      else if(wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    val destinationDirectory = findEntry(root, absolutePath)

    if(destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage("Incorrect destination path")
    else
      State(root, destinationDirectory.asDirectory)
  }
}
