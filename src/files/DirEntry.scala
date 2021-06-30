package com.sajag16642
package files

/**
 * @author sajag16642
 */
abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory

  def getType: String
}
