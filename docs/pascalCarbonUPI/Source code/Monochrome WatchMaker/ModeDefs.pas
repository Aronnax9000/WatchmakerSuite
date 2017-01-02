unit ModeDefs;

interface

	uses MacOSAll;
	
	type
		Mode = (
			preliminary, 
			breeding, 
			albuming, 
			phyloging, 
			killing, 
			moving, 
			detaching, 
			randoming, 
			engineering, 
			drifting, 
			highlighting, 
			PlayingBack, 
			triangling, 
			sweeping
		);
	
	var
		TheMode, SaveMode, OldMode, RememberMode: mode;

	procedure SetMode(theWindow: WindowRef; newMode: Mode);

implementation

	procedure SetMainWindowTitle(theWindow: WindowRef);
		begin
			case theMode of 
				highlighting: SetWindowTitleWithCFString(theWindow, CFSTR('Highlighting'));
				triangling: SetWindowTitleWithCFString(theWindow, CFSTR('Triangle'));
				breeding: SetWindowTitleWithCFString(theWindow, CFSTR('Breeding'));
				phyloging: SetWindowTitleWithCFString(theWindow, CFSTR('Pedigree (Draw out offspring)'));
				moving: SetWindowTitleWithCFString(theWindow, CFSTR('Pedigree (Move biomorph)'));
				detaching: SetWindowTitleWithCFString(theWindow, CFSTR('Pedigree (Detach biomorph)'));
				killing: SetWindowTitleWithCFString(theWindow, CFSTR('Pedigree (Kill biomorph)'));
				engineering: SetWindowTitleWithCFString(theWindow, CFSTR('Engineering'));
				Albuming: SetWindowTitleWithCFString(theWindow, CFSTR('Album'));
				Randoming:SetWindowTitleWithCFString(theWindow, CFSTR('Hopeful Monster'));
				Sweeping: SetWindowTitleWithCFString(theWindow, CFSTR('Sweeping')); 
				Drifting: SetWindowTitleWithCFString(theWindow, CFSTR('Drifting')); 
			end
		end;

	procedure SetMode(theWindow: WindowRef; newMode: Mode);
		begin
			if newMode <> TheMode then
				begin
					OldMode := TheMode;
					TheMode := newMode;
					SetMainWindowTitle(theWindow)
				end
		end;

end.