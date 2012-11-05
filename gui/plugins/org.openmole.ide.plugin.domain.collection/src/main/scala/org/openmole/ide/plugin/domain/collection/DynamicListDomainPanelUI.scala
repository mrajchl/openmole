/*
 * Copyright (C) 2011 <mathieu.Mathieu Leclaire at openmole.org>
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

package org.openmole.ide.plugin.domain.collection

import org.openmole.ide.core.model.dataproxy.IPrototypeDataProxyUI
import org.openmole.ide.core.model.panel.IDomainPanelUI
import org.openmole.ide.misc.widget.PluginPanel
import swing.ScrollPane.BarPolicy._
import swing._
import java.awt.Color

class DynamicListDomainPanelUI(pud: DynamicListDomainDataUI[_],
                               prototype: IPrototypeDataProxyUI) extends PluginPanel("") with IDomainPanelUI {

  val textArea = new TextArea(pud.values.mkString("\n"), 10, 20) {
    override val foreground = Color.black
  }

  contents += new ScrollPane(textArea) {
    horizontalScrollBarPolicy = Never
    verticalScrollBarPolicy = AsNeeded
  }

  def saveContent = DynamicListDomainDataUI(textArea.text.split('\n').toSet.toList,
    prototype.dataUI.toString)
}
