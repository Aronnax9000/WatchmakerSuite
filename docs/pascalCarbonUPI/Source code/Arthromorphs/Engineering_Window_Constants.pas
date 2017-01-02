unit Engineering_Window_Constants;

interface
	
	{$ifc undefined THINK_Pascal}
	uses MacOSAll;
	{$endc}

	const   								{These are the item numbers for controls in the Dialog}
		kEngrButtonOK = 1;
		kEngrSegAll = 2;
		kEngrSegNone = 3;
		kEngrMutAll = 4;
		kEngrMutNone = 5;
		kEngrButtonCancel = 6;
		kEngrSegAnimalTrunk = 7;
		kEngrSegAnimalLegs = 8;
		kEngrSegAnimalClaws = 9;
		kEngrSegSectionTrunk = 10;
		kEngrSegSectionLegs = 11;
		kEngrSegSectionClaws = 12;
		kEngrSegSegmentTrunk = 13;
		kEngrSegSegmentLegs = 14;
		kEngrSegSegmentClaws = 15;
		kEngrMutWidth = 16;
		kEngrMutHeight = 17;
		kEngrMutAngle = 18;
		kEngrMutDuplication = 19;
		kEngrMutDeletion = 20;
		kEngrSegLegs = 21;
		kEngrSegClaws = 22;
		kEngrPressurePositive = 23; {was I}
		kEngrPressureZero = 24;
		kEngrPressureNegative = 25;
		kEngrFocus1stSeg = 26;
		kEngrFocusLastSeg = 27;
		kEngrFocusNo = 28;
		kEngrPressure = 40;
		kEngrFocus = 41;
	procedure DrawEngrContent(RectOK: Rect);

implementation
	
	procedure DrawEngrContent(RectOK: Rect);
		var 
			tempRect: Rect;
		begin
			PenSize(3, 3);  			{Change pen to draw thick default outline}
			InsetRect(RectOK, -4, -4);{Draw outside the button by 1 pixel}
			FrameRoundRect(RectOK, 16, 16); {Draw the outline}
			PenSize(1, 1);  			{Restore the pen size to the default value}

			{Draw a rectangle, Rectangle1  }
			SetRect(TempRect, 18, 35, 170, 286);{left,top,right,bottom}
			FrameRect(TempRect);    	{Frame this rectangle area}

			{Draw a rectangle, Rectangle2  }
			SetRect(TempRect, 191, 36, 326, 196);{left,top,right,bottom}
			FrameRect(TempRect);    	{Frame this rectangle area}

			{Draw a rectangle, Rectangle4  }
			SetRect(TempRect, 192, 215, 327, 273);{left,top,right,bottom}
			FrameRect(TempRect);    	{Frame this rectangle area}

			{Draw a rectangle, Rectangle1  }
			SetRect(TempRect, 16, 292, 170, 361);{left,top,right,bottom}
			FrameRect(TempRect);    	{Frame this rectangle area}

	end;
end.