/*
 *  Copyright (C) 2010 Romain Reuillon <romain.reuillon at openmole.org>
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

package org.openmole.core.model.data;

/**
 *
 * @author Romain Reuillon <romain.reuillon at openmole.org>
 */
public enum DataModMask {

    /**
     *
     * Data is optionnal
     *
     */
    OPTIONAL(0x0001),

    /**
     *
     * State that the data value of a variable will not be modified
     *
     */
    IMMUTABLE(0x0002),

    /**
     *
     * State that the data value of a variable that is used for system level information
     * as oposed to buisiness level informations
     *
     */
    SYSTEM(0x0004);


    private int mask;

    private DataModMask(int mask) {
        this.mask = mask;
    }

    /**
     *
     * Get the value of the mask
     *
     * @return the value of the mask
     */
    public int getMask() {
        return mask;
    }
}
