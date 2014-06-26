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
package ua.at.tsvetkov.dataprocessor;

/**
 * Protocol for URL like "http" or "file". This is also known as the scheme. The returned string is lower case.
 * @author lordtao
 *
 */
public enum Scheme { HTTP("http://"), HTTPS("https://"), FILE("file://");
	
	private String	mType;

	private Scheme(String type) {
		this.mType = type;
	}

	/**
	 * Returns the protocol for URL like "http://" or "file://". This is also known as the scheme. The returned string is lower case.
	 * @return
	 */
	public String getString() {
		return mType;
	}
}
