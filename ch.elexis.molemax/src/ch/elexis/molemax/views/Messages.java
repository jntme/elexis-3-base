/*******************************************************************************
 * Copyright (c) 2007-2014 G. Weirich, A. Brögli and A. Häffner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    rgw - initial API and implementation
 *    rgw - 2014: Changes for Elexis 2.x
 ******************************************************************************/
package ch.elexis.molemax.views;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "ch.elexis.molemax.views.messages"; //$NON-NLS-1$
	public static String AllSlotsDisplay_back;
	public static String AllSlotsDisplay_front;
	public static String AllSlotsDisplay_left;
	public static String AllSlotsDisplay_right;
	public static String BaseSelectorDialog_pleaseSelect;
	public static String BaseSelectorDialog_selectSequence;
	public static String DetailDisplay_deleteFrame;
	public static String DetailDisplay_deleteFrameAndImage;
	public static String DetailDisplay_deleteReally;
	public static String ImageSlot_chosensequenceis;
	public static String ImageSlot_daysold;
	public static String ImageSlot_delete;
	public static String ImageSlot_deleteall;
	public static String ImageSlot_imageDel;
	public static String ImageSlot_imagesdelete;
	public static String ImageSlot_insufficientRights;
	public static String ImageSlot_insufficientRights2;
	public static String ImageSlot_newsequence;
	public static String ImageSlot_notPermitted;
	public static String ImageSlot_reallydelete;
	public static String ImageSlot_replace;
	public static String ImageSlot_these;
	public static String MolemaxPrefs_basedir;
	public static String Overview_baseDate;
	public static String Overview_noPatient;
	public static String Overview_selectSequence;
	public static String Overview_restore;
	public static String Overview_restoresequence;
	public static String RowDisplay_overview;
	public static String TimeMachineDisplay_back;
	public static String TimeMachineDisplay_date;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
