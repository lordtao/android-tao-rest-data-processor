/*******************************************************************************
 * Copyright (c) 2014 Alexandr Tsvetkov.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Contributors:
 *     Alexandr Tsvetkov - initial API and implementation
 *
 * Project:
 *     TAO Data Processor
 *
 * License agreement:
 *
 * 1. This code is published AS IS. Author is not responsible for any damage that can be
 *    caused by any application that uses this code.
 * 2. Author does not give a garantee, that this code is error free.
 * 3. This code can be used in NON-COMMERCIAL applications AS IS without any special
 *    permission from author.
 * 4. This code can be modified without any special permission from author IF AND ONLY IF
 *    this license agreement will remain unchanged.
 ******************************************************************************/
package ua.at.tsvetkov.data_processor.processors;

import java.io.InputStream;

import ua.at.tsvetkov.data_processor.interfaces.InputStreamDataInterface;
import ua.at.tsvetkov.data_processor.processors.abstractclasses.AbstractProcessor;

public class InputStreamProcessor<T> extends AbstractProcessor<T> {

   private InputStreamDataInterface object;

   public InputStreamProcessor(InputStreamDataInterface obj) {
      object = obj;
   }

   @Override
   public void parse(InputStream inputStream) throws Exception {
      object.fillFromInputStream(inputStream);
   }

   @SuppressWarnings("unchecked")
   @Override
   public T getResult() {
      return (T) object;
   }

}
