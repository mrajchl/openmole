/*
 *  Copyright (C) 2010 Mathieu Leclaire <mathieu.leclaire@openmole.org>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openmole.ui.ide.serializer;

import java.util.Iterator;
import org.openmole.commons.exception.UserBadDataError;
import org.openmole.core.implementation.mole.Mole;
import org.openmole.core.implementation.transition.Slot;
import org.openmole.core.implementation.transition.Transition;
import org.openmole.core.model.capsule.ICapsule;
import org.openmole.core.model.capsule.IGenericCapsule;
import org.openmole.core.model.mole.IMole;
import org.openmole.ui.ide.workflow.implementation.CapsuleModelUI;
import org.openmole.ui.ide.workflow.implementation.MoleSceneManager;
import org.openmole.ui.ide.workflow.model.ICapsuleModelUI;
import org.openmole.ui.ide.workflow.model.IMoleScene;

/**
 *
 * @author Mathieu Leclaire <mathieu.leclaire@openmole.org>
 */
public class MoleMaker {

    public static IMole process(IMoleScene scene) throws UserBadDataError {
        MoleSceneManager manager = scene.getManager();

        //First capsule
        ICapsuleModelUI start = manager.getStartingCapsule();
        if (start == CapsuleModelUI.EMPTY_CAPSULE_MODEL) {
            throw new UserBadDataError("A starting capsule is expected");
        }

        IMole mole = new Mole(explore(start));
        return mole;
    }

    public static IGenericCapsule explore(ICapsuleModelUI capsuleModel) throws UserBadDataError {
        IGenericCapsule capsule = CoreClassInstanciator.instanciateCapsule(capsuleModel);
        while (capsule.intputSlots().size() < capsuleModel.getNbInputslots()) {
            capsule.addInputSlot(new Slot(capsule));
        }
        if (capsuleModel.hasChild()) {
            for (Iterator<ICapsuleModelUI> child = capsuleModel.getChilds().iterator(); child.hasNext();) {
                new Transition((ICapsule) capsule, explore(child.next()));
            }
        }
        return capsule;
    }
}
