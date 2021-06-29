package com.sajag16642
package files

/**
 * @author sajag16642
 */
class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {

}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "");

  def empty(parentPath: String, name: String) =
    new Directory(parentPath, name, List())
}