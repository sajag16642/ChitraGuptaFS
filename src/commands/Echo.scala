package com.sajag16642
package commands

import filesystem.State

import com.sajag16642.files.{Directory, File}

import javax.lang.model.element.ModuleElement.Directive
import scala.annotation.tailrec

/**
 * @author sajag16642
 */
class Echo(args: Array[String]) extends Command {

  def createContent(args: Array[String], topIndex: Int): String = {
    @tailrec
    def createContentHelper(currentIndex: Int, acc: String): String = {
      if (currentIndex >= topIndex) acc
      else createContentHelper(currentIndex + 1, acc + " " + args(currentIndex))
    }

    createContentHelper(0, "")
  }

  def getRoot(currentDirectory: Directory, path: List[String], contents: String, appends: Boolean): Directory = {
    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)
      if (dirEntry == null) currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      else if(dirEntry.isDirectory) currentDirectory
      else
        if(appends) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContent(contents))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContent(contents))
    }
    else{
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val newNextDirectory = getRoot(nextDirectory, path.tail, contents, appends)

      if(newNextDirectory == nextDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDirectory)
    }
  }

  def doEcho(state: State, contents: String, fileName: String, append: Boolean): State = {
    if (isIllegal(fileName)) state.setMessage("filename should contains numbers and alphabets only!!")
    else {
      val newRoot: Directory = getRoot(state.root, state.wd.getAllFoldersInPath :+ fileName,contents, append)
      if (newRoot == state.root)
        state.setMessage(s"$fileName: no such file")
      else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  def isIllegal(str: String): Boolean = {
    !str.matches("^[a-zA-Z0-9]*$")
  }

  override def apply(state: State): State = {
    if (args.isEmpty) state
    else if (args.length == 1) state.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val fileName = args(args.length - 1)
      val contents = createContent(args, args.length - 1)

      if (">>".equals(operator))
        doEcho(state, contents, fileName, true)
      else if (">".equals(operator))
        doEcho(state, contents, fileName, false)
      else
        state.setMessage(createContent(args, args.length))
    }
  }
}
