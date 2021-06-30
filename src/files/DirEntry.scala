package com.sajag16642
package files

/**
 * @author sajag16642
 */
abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = {
    if(parentPath.equals(Directory.ROOT_PATH)) Directory.SEPARATOR + name
    else parentPath + Directory.SEPARATOR + name
  }

  def asDirectory: Directory
  def asFile: File

  def getType: String

  def isDirectory: Boolean
  def isFile: Boolean
}
