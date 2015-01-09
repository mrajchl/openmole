/*
 * Copyright (C) 2011 Romain Reuillon
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

package org.openmole.core.workflow.mole

import org.openmole.core.workflow.data._
import org.openmole.core.workflow.task._

object StrainerCapsule {
  def apply(task: Task) = new StrainerCapsule(task)

  class StrainerTaskDecorator(val task: Task) extends Task {
    override def inputs = task.inputs
    override def outputs = task.outputs
    override def plugins = task.plugins

    override def perform(context: Context) = process(context)
    override def process(context: Context) = context + task.perform(context)
    override def defaults = task.defaults

    override def name = task.name
  }

}

class StrainerCapsule(task: Task) extends Capsule(new StrainerCapsule.StrainerTaskDecorator(task)) with Strainer {

  override def inputs(mole: Mole, sources: Sources, hooks: Hooks) =
    received(mole, sources, hooks).filterNot(d ⇒ super.inputs(mole, sources, hooks).contains(d.prototype.name)) ++
      super.inputs(mole, sources, hooks)

  override def outputs(mole: Mole, sources: Sources, hooks: Hooks) =
    received(mole, sources, hooks).filterNot(d ⇒ super.outputs(mole, sources, hooks).contains(d.prototype.name)) ++
      super.outputs(mole, sources, hooks)

}