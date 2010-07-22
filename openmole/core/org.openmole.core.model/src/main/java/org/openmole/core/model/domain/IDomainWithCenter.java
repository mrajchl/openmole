/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openmole.core.model.domain;

import org.openmole.core.model.job.IContext;
import org.openmole.commons.exception.InternalProcessingError;
import org.openmole.commons.exception.UserBadDataError;

/**
 *
 * @author Romain Reuillon <romain.reuillon at openmole.org>
 */
public interface IDomainWithCenter<T>  {
    T getCenter(IContext global, IContext context) throws InternalProcessingError, UserBadDataError;
}
