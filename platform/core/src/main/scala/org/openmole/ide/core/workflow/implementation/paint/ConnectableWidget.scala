/*
 * Copyright (C) 2011 Mathieu leclaire <mathieu.leclaire at openmole.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.ide.core.workflow.implementation.paint

import java.awt.Color
import java.awt.Container
import java.awt.Font
import java.awt.Graphics2D
import org.openmole.ide.core.control.MoleScenesManager
import org.openmole.ide.core.palette.ElementFactories
import org.openmole.ide.core.properties.TaskPanelUIData
import org.openmole.ide.core.workflow.implementation.CapsuleViewUI
import org.openmole.ide.core.workflow.implementation.MoleScene
import org.netbeans.api.visual.action.ActionFactory
import org.openmole.ide.core.commons.Constants
import scala.collection.mutable.HashSet

class ConnectableWidget(scene: MoleScene, val capsuleView: CapsuleViewUI) extends MyWidget(scene, capsuleView){

  var islots= HashSet.empty[ISlotWidget]
  val oslot= new OSlotWidget(scene,capsuleView)
  var samplingWidget: Option[SamplingWidget] = None
  
  addChild(oslot)
  
  createActions(scene.MOVE).addAction(ActionFactory.createMoveAction)

  implicit def bool2int(b:Boolean) = if (b) 1 else 0

  def setDetailedView= {
    setWidthHint
    oslot.setDetailedView(taskWidth)
    if (samplingWidget.isDefined) samplingWidget.get.setDetailedView(taskWidth)
  }
  
  def addInputSlot(iw: ISlotWidget) {
    islots.add(iw)
    addChild(iw)
    scene.validate
  }
    
  def clearInputSlots= {
    islots.foreach(removeChild(_))
    islots.clear
  }
  
  def addSampling= {
    samplingWidget = Some(new SamplingWidget(scene,capsuleView))
    addChild(samplingWidget.get) 
    taskHeight += 58
    setWidthHint
    setDetailedView
  }
 
  override def paintWidget= {
    super.paintWidget
    val graphics = getGraphics.asInstanceOf[Graphics2D]
    graphics.setColor(new Color(204,204,204))
    graphics.setFont(new Font("Ubuntu", Font.PLAIN, 12))
    
    if (capsuleView.dataProxy.isDefined) {
      val panelUIData = capsuleView.dataProxy.get.panelUIData.asInstanceOf[TaskPanelUIData]
      var x = taskWidth / 2 + 5
      var i= 0
      var otherColumn = true
      (panelUIData.prototypesIn.toList:::panelUIData.prototypesOut.toList).foreach(p=> {
          if (i >= panelUIData.prototypesIn.size && otherColumn == true) {
            i= 0
            x += taskWidth / 2 - 1
            otherColumn = false
          }
          var st = p.panelUIData.name
          if (st.length> 10) st = st.substring(0, 8).concat("...")
          val h = 5 + Constants.TASK_TITLE_HEIGHT + i * Images.THUMB_SIZE
          graphics.drawImage(Images.thumb(p.panelUIData.imagePath),x - taskWidth / 2, h ,new Container)
          graphics.setColor(new Color(102,102,102))
          if (MoleScenesManager.detailedView) graphics.drawString(st, 1 + x - taskWidth / 2 +  Images.THUMB_SIZE, h + Images.THUMB_SIZE / 2)
          i+= 1
        })

      val newH= scala.math.max(panelUIData.prototypesIn.size, panelUIData.prototypesOut.size) * 22 + 45
      val delta= bodyArea.height - newH
      if (delta < 0) {
        bodyArea.setSize(bodyArea.width, newH)
        enlargeWidgetArea(0, -delta)
      }
      var lineH = 0
      if (samplingWidget.isDefined) lineH = samplingWidget.get.capsule.dataProxy.get.panelUIData.asInstanceOf[TaskPanelUIData].sampling.isDefined * 58
      graphics.drawLine(taskWidth / 2,
                        Constants.TASK_TITLE_HEIGHT,
                        taskWidth / 2,
                        Constants.TASK_CONTAINER_HEIGHT - 3 + lineH)
    }
    revalidate
  }
  
}