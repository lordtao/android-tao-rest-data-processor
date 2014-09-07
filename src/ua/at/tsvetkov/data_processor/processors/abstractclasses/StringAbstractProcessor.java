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
package ua.at.tsvetkov.data_processor.processors.abstractclasses;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import ua.at.tsvetkov.util.Log;

/**
 * Base String parser. Can be implemented for parse JSON, CSV and etc. data.
 * 
 * @author lordtao
 * @param <T>
 */
public abstract class StringAbstractProcessor<T> extends AbstractProcessor<T> {

   @Override
   public void parse(InputStream inputStream) throws Exception {
      if (inputStream == null) {
         Log.w("InputStream is null. Parsing aborted.");
         return;
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      StringBuffer buffer = new StringBuffer();
      while ((line = reader.readLine()) != null) {
         buffer.append(line);
      }
      process(buffer.toString().trim());
   }

   @Override
   public abstract T getResult();

   /**
    * Processing the received string.
    * 
    * @param src
    */
   public abstract void process(String src) throws Exception;

}
