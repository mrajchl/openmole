/*
 * Copyright (C) 2012 Romain Reuillon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.plugin.environment.pbs

import fr.iscpif.gridscale.ssh.SSHStorage
import fr.iscpif.gridscale.ssh.SSHHost
import java.net.URI
import org.openmole.core.batch.control.LimitedAccess
import org.openmole.core.batch.environment._
import org.openmole.core.batch.storage.PersistentStorageService
import org.openmole.core.batch.storage.StorageService
import org.openmole.misc.workspace._
import org.openmole.plugin.environment.gridscale._
import org.openmole.plugin.environment.ssh._

import scala.concurrent.duration.Duration

object PBSEnvironment {

  def apply(
    user: String,
    host: String,
    port: Int = 22,
    queue: Option[String] = None,
    openMOLEMemory: Option[Int] = None,
    wallTime: Option[Duration] = None,
    memory: Option[Int] = None,
    nodes: Option[Int] = None,
    coreByNode: Option[Int] = None,
    workDirectory: Option[String] = None,
    threads: Option[Int] = None)(implicit authentications: AuthenticationProvider) =
    new PBSEnvironment(user, host, port, queue, openMOLEMemory, wallTime, memory, nodes, coreByNode, workDirectory, threads)
}

import PBSEnvironment._

class PBSEnvironment(
    val user: String,
    val host: String,
    override val port: Int,
    val queue: Option[String],
    override val openMOLEMemory: Option[Int],
    val wallTime: Option[Duration],
    val memory: Option[Int],
    val nodes: Option[Int],
    val coreByNode: Option[Int],
    val workDirectory: Option[String],
    override val threads: Option[Int])(implicit authentications: AuthenticationProvider) extends BatchEnvironment with SSHPersistentStorage with MemoryRequirement { env ⇒

  type JS = PBSJobService

  @transient lazy val credential = SSHAuthentication(user, host, port, authentications)(authentications)
  def id = new URI("pbs", env.user, env.host, env.port, null, null, null).toString

  @transient lazy val jobService = new PBSJobService with ThisHost with LimitedAccess {
    def nbTokens = maxConnections
    def queue = env.queue
    val environment = env
    def sharedFS = storage
  }

  def allJobServices = List(jobService)

}
