unit PicDefs;

interface

uses MacOSAll, PersonDefs, ErrorUnit;

const
	PicSizeMax = 32767;
	

type
	LinPtr = ^Lin;
	Lin = RECORD
			next: LinPtr;
			StartPt: HIPoint; 
			EndPt: HIPoint;
			Thickness: CGFLoat;
		end;
	
	MorphPicPtr = ^MorphPic;
	{In classic Watchmaker this was just called "Pic", but that name collides with a quickdraw data type - ABC}
	{This is one of the few places where pointer arithmetic is done, which is maybe not the best approach.}
	{Probably better to replace it with a linked list implementation.}
	MorphPic = RECORD
			First: LinPtr;
			Last: LinPtr; {Speed addition of new Lins}
			Origin: HIPoint;
			PicSize: LongInt;
			PicPerson: PersonPtr;
		end;

	procedure MorphPicCreate(var newPicPtr: MorphPicPtr);
	procedure ZeroPic (ThisPic: MorphPicPtr; Here: HIPoint);
	procedure PicLine (ThisPic: MorphPicPtr; x, y, xnew, ynew: CGFloat; thick: CGFloat);
implementation


	procedure MorphPicCreate(var newPicPtr: MorphPicPtr);
		begin
			new(newPicPtr);
			newPicPtr^.First := nil;
			newPicPtr^.Last := nil;
			newPicPtr^.PicSize := 0;
		{
			LinSize := SizeOf(Lin);
			SizeNeeded := LONGINT(LinSize * PicSizeMax);
			MyPic.BasePtr := NewPtr(LongInt(SizeNeeded));
			}
		end;
		

procedure PicLine (ThisPic: MorphPicPtr; x, y, xnew, ynew: CGFloat; thick: CGFloat);
	var	
		newLin: LinPtr;
	begin
		new(newLin);
		
		newLin^.next := nil;
		if ThisPic^.first = nil then
			ThisPic^.first := newLin;
			
		if ThisPic^.last <> nil then
			ThisPic^.last^.next := newLin;
			
		ThisPic^.last := newLin;
		IF thick > 8 THEN
			thick := 8;
		WITH ThisPic^ DO
			begin
				IF PicSize >= PicSizeMax THEN
					begin
						DisplayError(-147, 'Biomorph too large, or other problem', ' ', StopError);
						ExitToShell
					end
				ELSE
					WITH newLin^ DO
						begin
							StartPt.x := x;
							StartPt.y := y;
							EndPt.x := xnew;
							EndPt.y := ynew;
							Thickness := Thick
						end;
				PicSize := PicSize + 1
			end
	end; {PicLine}

procedure ZeroPic (ThisPic: MorphPicPtr; Here: HIPoint);
	var 
		markedForDeath: LinPtr;
		theLinPtr: LinPtr;
	begin
		WITH ThisPic^ DO
			begin
			theLinPtr := ThisPic^.First;
			while theLinPtr <> nil do
				begin
					markedForDeath := theLinPtr;
					theLinPtr := theLinPtr^.Next;
					Dispose(markedForDeath);
				end;
			ThisPic^.First := nil;
			ThisPic^.Last := nil;
			PicSize := 0;
			Origin := Here
			end
	end; {ZeroPic}

end.